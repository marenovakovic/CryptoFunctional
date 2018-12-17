package com.marko.cryptofunctional.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.marko.cryptofunctional.base.BaseViewModel
import com.marko.cryptofunctional.data.CoinsService
import com.marko.cryptofunctional.dispatchers.CoroutineDispatchers
import com.marko.cryptofunctional.entities.Coin
import com.marko.cryptofunctional.event.Event
import com.marko.cryptofunctional.usecases.FetchCoins
import javax.inject.Inject

/**
 * [ViewModel] for fetching [Coin] [List]
 *
 * @param dispatchers [CoroutineDispatchers] abstraction
 *
 * @param coinsService [CoinsService] for interacting with API
 */
class CoinsViewModel @Inject constructor(
	dispatchers: CoroutineDispatchers,
	private val coinsService: CoinsService
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
	 * [MutableLiveData] containing [Exception] that occurred during fetching, exposed as [LiveData]
	 */
	private val _error = MutableLiveData<Throwable>()
	val error: LiveData<Throwable>
		get() = _error

	/**
	 * Start fetching flow
	 */
	fun fetch() {
		_loading.value = Event(true)
		FetchCoins(
			context = coroutineContext,
			service = coinsService
		) {
			_loading.postValue(Event(false))
			it.fold(_error::postValue, _coins::postValue)
		}
	}
}