package com.marko.cryptofunctional.domain

import arrow.core.fix
import arrow.effects.unsafeRunAsync
import arrow.effects.unsafeRunSync
import com.marko.cryptofunctional.ScopedTest
import com.marko.cryptofunctional.data.CoinsService
import com.marko.cryptofunctional.entities.Coin
import com.marko.cryptofunctional.entities.CoinResponse
import com.marko.cryptofunctional.entities.CoinStatus
import com.marko.cryptofunctional.factory.CoinsFactory
import com.marko.cryptofunctional.injection.CoinsContext
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.CompletableDeferred
import org.junit.jupiter.api.Test

internal class FetchCoinDetailsUseCaseTest : ScopedTest() {

	private val coinsService = mockk<CoinsService>()
	private val coinsContext = CoinsContext(scope = this, coinsService = coinsService)

	@Test
	fun `does fetchCoinDetails runs without errors`() {
		val coin = CoinsFactory.createCoin()
		stubCoin(coin)

		CoinUseCases.fetchCoinDetails(1).run(coinsContext).fix().value.unsafeRunSync()
	}

	@Test
	fun `check fetchCoinDetails result`() {
		val coinId = 1

		val coin = CoinsFactory.createCoin(id = coinId)
		stubCoin(coin)

		val result =
			CoinUseCases.fetchCoinDetails(coinId).run(coinsContext).fix().value.unsafeRunSync()

		assert(result.id == coinId)
		assert(result == coin)
	}

	@Test
	fun `check error case`() {
		val exception = IllegalAccessException("jeb' se")
		stubException(exception)

		val coinId = 1

		CoinUseCases.fetchCoinDetails(coinId).run(coinsContext).fix().value.unsafeRunAsync {
			it.fold({ assert(it == exception) }, {})
		}
	}

	private fun stubCoin(coin: Coin) {
		val response = CoinResponse(coin = coin, status = CoinStatus())
		every { coinsService.getCoinDetails(any(), any()) } returns CompletableDeferred(response)
	}

	private fun stubException(exception: Exception) {
		every { coinsService.getCoinDetails(any(), any()) } throws exception
	}
}