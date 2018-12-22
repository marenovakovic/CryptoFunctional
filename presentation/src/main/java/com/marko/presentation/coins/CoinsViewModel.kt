package com.marko.presentation.coins

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import arrow.effects.ForDeferredK
import com.marko.data.coins.CoinRepository
import com.marko.domain.dispatchers.CoroutineDispatchers
import com.marko.presentation.base.BaseViewModel
import com.marko.presentation.entities.Coin
import com.marko.presentation.event.Event
import com.marko.presentation.extensions.executeWithLoading
import com.marko.presentation.mappers.toPresentation
import com.marko.usecases.FetchCoins
import javax.inject.Inject

/**
 * [ViewModel] for fetching coin related content
 *
 * @param dispatchers [CoroutineDispatchers]
 *
 * @param coinRepository [CoinRepository]
 */
class CoinsViewModel @Inject constructor(
	dispatchers: CoroutineDispatchers,
	private val coinRepository: CoinRepository<ForDeferredK>
) : BaseViewModel(dispatchers = dispatchers) {

	/**
	 * [MutableLiveData] holding [Event] signaling when loading should be shown or hidden, exposed as [LiveData]
	 */
	private val _loading = MutableLiveData<Event<Boolean>>()
	val loading: LiveData<Event<Boolean>>
		get() = _loading

	/**
	 * [MutableLiveData] holding fetched [Coin] [List], exposed as [LiveData]
	 */
	private val _coins = MutableLiveData<List<Coin>>()
	val coins: LiveData<List<Coin>>
		get() = _coins

	/**
	 * [MutableLiveData] holding [Throwable] thrown during fetching, exposed as [LiveData]
	 */
	private val _error = MutableLiveData<Throwable>()
	val error: LiveData<Throwable>
		get() = _error

	/**
	 * Start fetching flow
	 */
	fun fetch() = executeWithLoading(
		loading = _loading,
		success = _coins,
		error = _error,
		deferred = FetchCoins(coinRepository = coinRepository).map { it.toPresentation() }
	)
}