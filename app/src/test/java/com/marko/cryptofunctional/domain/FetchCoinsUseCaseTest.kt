package com.marko.cryptofunctional.domain

import com.marko.cryptofunctional.data.CoinsService
import com.marko.cryptofunctional.entities.Coin
import com.marko.cryptofunctional.entities.CoinsMetadata
import com.marko.cryptofunctional.entities.CoinsResponse
import com.marko.cryptofunctional.factory.CoinsFactory
import com.marko.cryptofunctional.usecases.FetchCoins
import io.kotlintest.assertions.arrow.either.shouldBeRight
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers

internal class FetchCoinsUseCaseTest : StringSpec() {

	init {

		"test FetchCoinsUseCase success case" {
			val coinsService = mockk<CoinsService>()

			val coins = CoinsFactory.coins
			coinsService.stubCoins(coins)

			FetchCoins(
				context = Dispatchers.Unconfined,
				service = coinsService
			) {
				it.shouldBeRight(coins)
			}
		}

		"test FetchCoinsUseCase failure case" {
			val coinsService = mockk<CoinsService>()

			val throwable = Throwable("jeb' se")
			coinsService.stubThrow(throwable)

			FetchCoins(
				context = Dispatchers.Unconfined,
				service = coinsService
			) {
				it.shouldBeRight()
				5 shouldBe 4
			}
		}
	}

	private fun CoinsService.stubCoins(coins: List<Coin>) {
		val response = CoinsResponse(coins = coins, metadata = CoinsMetadata())

		every { getCoins() } returns CompletableDeferred(response)
	}

	private fun CoinsService.stubThrow(throwable: Throwable) {
		every { getCoins() } throws throwable
	}
}