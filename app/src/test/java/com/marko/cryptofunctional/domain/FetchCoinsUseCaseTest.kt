package com.marko.cryptofunctional.domain

import arrow.core.fix
import arrow.effects.unsafeRunAsync
import arrow.effects.unsafeRunSync
import com.marko.cryptofunctional.ScopedTest
import com.marko.cryptofunctional.data.CoinsService
import com.marko.cryptofunctional.entities.Coin
import com.marko.cryptofunctional.entities.CoinsMetadata
import com.marko.cryptofunctional.entities.CoinsResponse
import com.marko.cryptofunctional.injection.CoinsContext
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.CompletableDeferred
import org.junit.jupiter.api.Test

internal class FetchCoinsUseCaseTest : ScopedTest() {

	private val coinsService = mockk<CoinsService>()
	private val testContext = CoinsContext(scope = this, coinsService = coinsService)

	@Test
	fun `does fetchCoinsUseCase runs without error`() {
		val coins = listOf<Coin>()
		stubCoins(coins)

		CoinUseCases.fetchCoins().run(testContext).fix().value.unsafeRunSync()
	}

	@Test
	fun `is service called when fetchCoinsUseCase is executed`() {
		val coins = listOf<Coin>()
		stubCoins(coins)

		CoinUseCases.fetchCoins().run(testContext).fix().value.unsafeRunSync()

		verify(exactly = 1) { coinsService.getCoins() }
	}

	@Test
	fun `check fetchCoinsUseCase result`() {
		val coins = listOf<Coin>()
		stubCoins(coins)

		val result = CoinUseCases.fetchCoins().run(testContext).fix().value.unsafeRunSync()

		result == coins
	}

	@Test
	fun `check error case`() {
		val exception = Exception("jeb' se")
		stubException(exception)

		CoinUseCases.fetchCoins().run(testContext).fix().value.unsafeRunAsync {
			it.fold({ assert(it != exception.cause) }, {})
		}
	}

	private fun stubCoins(coins: List<Coin>) {
		val response = CoinsResponse(coins = coins, metadata = CoinsMetadata())

		every { coinsService.getCoins() } returns CompletableDeferred(response)
	}

	private fun stubException(exception: Exception) {
		every { coinsService.getCoins() } throws exception
	}
}