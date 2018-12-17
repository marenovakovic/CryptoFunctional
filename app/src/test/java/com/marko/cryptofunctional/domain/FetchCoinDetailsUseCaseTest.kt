package com.marko.cryptofunctional.domain

import com.marko.cryptofunctional.ScopedTest
import com.marko.cryptofunctional.data.CoinsService
import com.marko.cryptofunctional.entities.Coin
import com.marko.cryptofunctional.entities.CoinResponse
import com.marko.cryptofunctional.entities.CoinStatus
import com.marko.cryptofunctional.injection.CoinsContext
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.CompletableDeferred

internal class FetchCoinDetailsUseCaseTest : ScopedTest() {

	private val coinsService = mockk<CoinsService>()
	private val coinsContext = CoinsContext(scope = this, coinsService = coinsService)


	private fun stubCoin(coin: Coin) {
		val response = CoinResponse(coin = coin, status = CoinStatus())
		every { coinsService.getCoinDetails(any(), any()) } returns CompletableDeferred(response)
	}

	private fun stubException(exception: Exception) {
		every { coinsService.getCoinDetails(any(), any()) } throws exception
	}
}