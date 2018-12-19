package com.marko.presentation.coins

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import arrow.effects.DeferredK
import arrow.effects.ForDeferredK
import com.marko.data.coins.CoinRepository
import com.marko.domain.entities.CoinEntity
import com.marko.presentation.PresentationCoinsFactory
import com.marko.presentation.dispatchers.TestCoroutineDispatchers
import com.marko.presentation.entities.Coin
import com.marko.presentation.mappers.toPresentation
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
internal class CoinsViewModelTest {

	@get:Rule
	val rule = InstantTaskExecutorRule()

	private val dispatchers = TestCoroutineDispatchers()
	private val coinRepository = mockk<CoinRepository<ForDeferredK>>()
	private val coinsViewModel = CoinsViewModel(dispatchers, coinRepository)

	@Test
	fun `check viewModel fetching result when no exceptions are thrown`() {
		val coins = PresentationCoinsFactory.coinEntities
		coinRepository.stubCoins(coins)

		val observer = mockObserver<List<Coin>>().stubObserving()
		val errorObserver = mockObserver<Throwable>().stubObserving()

		coinsViewModel.fetch()

		coinsViewModel.coins.observeForever(observer)
		coinsViewModel.error.observeForever(errorObserver)

		verify(exactly = 1) { observer.onChanged(coins.toPresentation()) }
		verify(exactly = 0) { errorObserver.onChanged(any()) }
	}

	@Test
	fun `check error event is posted when exception is thrown`() {
		val throwable = Throwable("jeb' se")
		coinRepository.stubThrow(throwable)

		val observer = mockObserver<List<Coin>>().stubObserving()
		val errorObserver = mockObserver<Throwable>().stubObserving()

		coinsViewModel.fetch()

		coinsViewModel.coins.observeForever(observer)
		coinsViewModel.error.observeForever(errorObserver)

		verify(exactly = 0) { observer.onChanged(any()) }
		verify(exactly = 1) { errorObserver.onChanged(throwable) }
	}

	private fun CoinRepository<ForDeferredK>.stubCoins(coins: List<CoinEntity>) {
		every { fetch() } returns DeferredK.just(coins)
	}

	private fun CoinRepository<ForDeferredK>.stubThrow(throwable: Throwable) {
		every { fetch() } throws throwable
	}

	private fun <T> mockObserver() = mockk<Observer<T>>()

	private inline fun <reified T : Any> Observer<T>.stubObserving(): Observer<T> {
		every { onChanged(any()) } returns Unit

		return this
	}
}