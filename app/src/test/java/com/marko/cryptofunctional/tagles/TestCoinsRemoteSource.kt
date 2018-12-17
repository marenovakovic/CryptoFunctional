package com.marko.cryptofunctional.tagles

import arrow.effects.*
import arrow.effects.deferredk.async.async
import arrow.effects.typeclasses.Async
import com.marko.cryptofunctional.data.CoinsRemoteSource
import com.marko.cryptofunctional.data.CoinsService
import com.marko.cryptofunctional.entities.*
import com.marko.cryptofunctional.factory.CoinsFactory
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.CompletableDeferred

class TestCoinsRemoteSource : StringSpec() {

	init {
		"does fetchCoins completes" {
			val coinsService = mockk<CoinsService>()
			val coins = CoinsFactory.coins
			coinsService.stub(coins)

			val remoteSource =
				object : CoinsRemoteSource<ForDeferredK>, Async<ForDeferredK> by DeferredK.async() {
					override val service: CoinsService
						get() = coinsService
				}

			remoteSource.fetchCoins().fix().unsafeRunSync()
		}

		"does fetchCoins returns correct result" {
			val coinsService = mockk<CoinsService>()
			val coins = CoinsFactory.coins
			coinsService.stub(coins)

			val remoteSource =
				object : CoinsRemoteSource<ForDeferredK>, Async<ForDeferredK> by DeferredK.async() {
					override val service: CoinsService
						get() = coinsService
				}

			val result = remoteSource.fetchCoins().fix().unsafeRunSync()

			result shouldBe coins
		}

		"test fetchCoin" {
			val coinsService = mockk<CoinsService>()
			val coin = CoinsFactory.createCoin()
			coinsService.stubCoin(coin)

			val remoteSource =
				object : CoinsRemoteSource<ForDeferredK>, Async<ForDeferredK> by DeferredK.async() {
					override val service: CoinsService
						get() = coinsService
				}

			val coinId = 1

			val result = remoteSource.fetchCoinDetails(coinId).fix().unsafeRunSync()

			result shouldBe coin
		}
	}

	private fun CoinsService.stub(coins: List<Coin>) {

		val response = CoinsResponse(coins = coins, metadata = CoinsMetadata())

		every { this@stub.getCoins() } returns CompletableDeferred(response)
	}

	private fun CoinsService.stubCoin(coin: Coin) {
		val response = CoinResponse(coin = coin, status = CoinStatus())

		every { this@stubCoin.getCoinDetails(any(), any()) } returns CompletableDeferred(response)
	}

	private fun CoinsService.stubThrow(throwable: Throwable) {
		every { this@stubThrow.getCoins() } throws throwable
	}
}