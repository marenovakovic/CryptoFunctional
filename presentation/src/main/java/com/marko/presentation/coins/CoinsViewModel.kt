package com.marko.presentation.coins

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import arrow.effects.ForDeferredK
import arrow.effects.unsafeRunAsync
import com.marko.data.coins.CoinRepository
import com.marko.domain.dispatchers.CoroutineDispatchers
import com.marko.presentation.base.BaseViewModel
import com.marko.presentation.entities.Coin
import com.marko.presentation.event.Event
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
) : BaseViewModel(dispatchers) {

	/**
	 * [MutableLiveData] containing [Event] signaling when loading should be shown or hidden, exposed as [LiveData]
	 */
	private val _loading = MutableLiveData<Event<Boolean>>()
	val loading: LiveData<Event<Boolean>>
		get() = _loading

	/**
	 * [MutableLiveData] containing fetched [Coin] [List], exposed as [LiveData]
	 */
	private val _coins = MutableLiveData<List<Coin>>()
	val coins: LiveData<List<Coin>>
		get() = _coins

	/**
	 * [MutableLiveData] containing [Throwable] thrown during fetching, exposed as [LiveData]
	 */
	private val _error = MutableLiveData<Throwable>()
	val error: LiveData<Throwable>
		get() = _error

	/**
	 * Start fetching flow
	 */
	fun fetch() {
		_loading.value = Event(true)
		FetchCoins(coinRepository = coinRepository).unsafeRunAsync {
			_loading.postValue(Event(false))
			it.map { it.toPresentation() }.fold(_error::postValue, _coins::postValue)
		}
	}
}