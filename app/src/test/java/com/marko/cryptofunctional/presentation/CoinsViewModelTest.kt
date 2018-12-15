package com.marko.cryptofunctional.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.marko.cryptofunctional.ScopedTest
import com.marko.cryptofunctional.dispatchers.TestCoroutineDispatchers
import com.marko.cryptofunctional.entities.Coin
import com.marko.cryptofunctional.entities.CoinsMetadata
import com.marko.cryptofunctional.entities.CoinsResponse
import com.marko.cryptofunctional.factory.CoinsFactory
import com.marko.cryptofunctional.home.CoinsViewModel
import com.marko.cryptofunctional.injection.CoinsContext
import com.marko.cryptofunctional.event.Event
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.CompletableDeferred
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
internal class CoinsViewModelTest : ScopedTest() {

	@get:Rule
	val rule = InstantTaskExecutorRule()

	private val dispatchers = TestCoroutineDispatchers()

	private val viewModel = CoinsViewModel(dispatchers)

	private val coinsContext = CoinsContext(mockk(), mockk())

	@Test
	fun `does fetching beings with loading event`() {
		val coins = CoinsFactory.coins
		stubContext(coinsContext, coins)
		viewModel.fetch(coinsContext)

		val observer = mockObserver<Event<Boolean>>()
		stubOnChange(observer)

		viewModel.loading.observeForever(observer)

		verify { observer.onChanged(Event(true)) }
	}

	@Test
	fun `check success case`() {
		val coins = CoinsFactory.coins
		stubContext(coinsContext, coins)
		viewModel.fetch(coinsContext)

		val observer = mockObserver<List<Coin>>()
		stubOnChange(observer)

		viewModel.coins.observeForever(observer)

		verify { observer.onChanged(coins) }
	}

	@Test
	fun `check error case`() {
		val exception = IllegalAccessException("jeb' se")
		stubException(coinsContext, exception)
		viewModel.fetch(coinsContext)

		val observer = mockObserver<Throwable>()
		stubOnChange(observer)

		viewModel.error.observeForever(observer)

		verify { observer.onChanged(exception) }
	}

	private fun stubContext(coinsContext: CoinsContext, coins: List<Coin>) {
		every { coinsContext.scope.coroutineContext } returns coroutineContext

		val response = CoinsResponse(coins, CoinsMetadata())
		every { coinsContext.coinsService.getCoins() } returns CompletableDeferred(response)
	}

	private fun stubException(coinsContext: CoinsContext, throwable: Throwable) {
		every { coinsContext.scope.coroutineContext } returns coroutineContext
		every { coinsContext.coinsService.getCoins() } throws throwable
	}

	private inline fun <reified T : Any> stubOnChange(observer: Observer<T>) {
		every { observer.onChanged(any()) } returns Unit
	}

	private fun <T> mockObserver() = mockk<Observer<T>>()
}