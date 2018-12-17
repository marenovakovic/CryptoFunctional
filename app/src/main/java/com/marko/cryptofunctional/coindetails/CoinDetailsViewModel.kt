package com.marko.cryptofunctional.coindetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.marko.cryptofunctional.base.BaseViewModel
import com.marko.cryptofunctional.data.CoinsService
import com.marko.cryptofunctional.dispatchers.CoroutineDispatchers
import com.marko.cryptofunctional.entities.Coin
import com.marko.cryptofunctional.entities.CoinId
import com.marko.cryptofunctional.event.Event
import com.marko.cryptofunctional.usecases.FetchCoinDetail
import javax.inject.Inject

/**
 * [ViewModel] for fetching [Coin] details
 *
 * @param dispatchers [CoroutineDispatchers] abstraction
 *
 * @param coinsService [CoinsService] for interacting with API
 */
class CoinDetailsViewModel @Inject constructor(
	dispatchers: CoroutineDispatchers,
	private val coinsService: CoinsService
) : BaseViewModel(dispatchers) {

	/**
	 * [MutableLiveData] holding [Event] signaling should loading be shown or hidden, exposed as [LiveData]
	 */
	private val _loading = MutableLiveData<Event<Boolean>>()
	val loading: LiveData<Event<Boolean>>
		get() = _loading

	/**
	 * [MutableLiveData] holding [Coin], exposed as [LiveData]
	 */
	private val _coinDetails = MutableLiveData<Coin>()
	val coinDetails: LiveData<Coin>
		get() = _coinDetails

	/**
	 * [MutableLiveData] holding [Exception] that occurred during fetching, exposed as [LiveData]
	 */
	private val _error = MutableLiveData<Throwable>()
	val error: LiveData<Throwable>
		get() = _error

	/**
	 * Start fetching flow
	 */
	fun fetch(coinId: CoinId) {
		_loading.value = Event(true)
		FetchCoinDetail(
			coinId = coinId,
			context = coroutineContext,
			service = coinsService
		) {
			_loading.postValue(Event(false))
			it.fold(_error::postValue, _coinDetails::postValue)
		}
	}
}