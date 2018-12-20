package com.marko.presentation.coindetails

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
internal class CoinDetailsViewModelTest {

	@get:Rule
	val rule = InstantTaskExecutorRule()

	private val dispatchers = TestCoroutineDispatchers()
	private val repository = mockk<CoinRepository<ForDeferredK>>()
	private val viewModel = CoinDetailsViewModel(dispatchers, repository)

	@Test
	fun `check fetchCoinDetails result when no exception is thrown`() {
		val observer = mockObserver<Coin>().stubObserving()
		val errorObserver = mockObserver<Throwable>()

		viewModel.coinDetails.observeForever(observer)
		viewModel.error.observeForever(errorObserver)

		val coinId = 1

		val coin = PresentationCoinsFactory.createCoinEntity(id = coinId)
		repository.stubCoin(coin)

		viewModel.fetch(coinId)

		verify(exactly = 1) { observer.onChanged(coin.toPresentation()) }
		verify(exactly = 0) { errorObserver.onChanged(any()) }
	}

	@Test
	fun `check fetchCoinDetails result when some exception is thrown`() {
		val throwable = Throwable("jeb' se")
		repository.stubThrow(throwable)

		val observer = mockObserver<Coin>().stubObserving()
		val errorObserver = mockObserver<Throwable>().stubObserving()

		viewModel.coinDetails.observeForever(observer)
		viewModel.error.observeForever(errorObserver)

		val coinId = 1

		viewModel.fetch(coinId)

		verify(exactly = 1) { errorObserver.onChanged(throwable) }
		verify(exactly = 0) { observer.onChanged(any()) }
	}

	private fun CoinRepository<ForDeferredK>.stubCoin(coin: CoinEntity) {
		every { fetchCoin(any()) } returns DeferredK.just(coin)
	}

	private fun CoinRepository<ForDeferredK>.stubThrow(throwable: Throwable) {
		every { fetchCoin(any()) } throws throwable
	}

	private fun <T> mockObserver() = mockk<Observer<T>>()

	private inline fun <reified T : Any> Observer<T>.stubObserving(): Observer<T> {
		every { onChanged(any()) } returns Unit

		return this
	}
}