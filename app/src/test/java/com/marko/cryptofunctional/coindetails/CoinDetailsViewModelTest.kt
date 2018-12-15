package com.marko.cryptofunctional.coindetails

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.marko.cryptofunctional.ScopedTest
import com.marko.cryptofunctional.dispatchers.TestCoroutineDispatchers
import com.marko.cryptofunctional.entities.Coin
import com.marko.cryptofunctional.entities.CoinResponse
import com.marko.cryptofunctional.entities.CoinStatus
import com.marko.cryptofunctional.factory.CoinsFactory
import com.marko.cryptofunctional.injection.CoinsContext
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.CompletableDeferred
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
internal class CoinDetailsViewModelTest : ScopedTest() {

	@get:Rule
	val rule = InstantTaskExecutorRule()

	private val dispatchers = TestCoroutineDispatchers()
	private val viewModel = CoinDetailsViewModel(dispatchers)

	private val coinsContext = CoinsContext(mockk(), mockk())

	@Test
	fun `check success case`() {
		val observer = mockObserver<Coin>()
		stubObserver(observer)

		val coinId = 1
		val coin = CoinsFactory.createCoin(id = coinId)

		stubResponse(coin)

		viewModel.fetch(coinId = coinId, coinsContext = coinsContext)

		viewModel.coinDetails.observeForever(observer)

		verify(exactly = 1) { observer.onChanged(coin) }
	}

	@Test
	fun `check error case`() {
		val observer = mockObserver<Throwable>()
		stubObserver(observer)

		val exception = IllegalAccessException("jeb' se")
		stubException(exception)

		viewModel.fetch(coinId = 1, coinsContext = coinsContext)

		viewModel.error.observeForever(observer)

		verify(exactly = 1) { observer.onChanged(exception) }
	}

	private fun stubResponse(coin: Coin) {
		every { coinsContext.scope.coroutineContext } returns coroutineContext

		val response = CoinResponse(coin = coin, status = CoinStatus())
		every {
			coinsContext.coinsService.getCoinDetails(any(), any())
		} returns CompletableDeferred(response)
	}

	private fun stubException(exception: Exception) {
		every { coinsContext.scope.coroutineContext } returns coroutineContext
		every { coinsContext.coinsService.getCoinDetails(any(), any()) } throws exception
	}

	private inline fun <reified T : Any> stubObserver(observer: Observer<T>) {
		every { observer.onChanged(any()) } returns Unit
	}

	private fun <T> mockObserver() = mockk<Observer<T>>()
}