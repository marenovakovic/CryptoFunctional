package com.marko.cryptofunctional.coindetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import arrow.core.fix
import arrow.effects.unsafeRunAsync
import com.marko.cryptofunctional.base.BaseViewModel
import com.marko.cryptofunctional.dispatchers.CoroutineDispatchers
import com.marko.cryptofunctional.domain.CoinUseCases
import com.marko.cryptofunctional.entities.Coin
import com.marko.cryptofunctional.entities.CoinId
import com.marko.cryptofunctional.event.Event
import com.marko.cryptofunctional.injection.CoinsContext
import javax.inject.Inject

/**
 * [ViewModel] for fetching [Coin] details
 *
 * @param dispatchers [CoroutineDispatchers] abstraction
 */
class CoinDetailsViewModel @Inject constructor(
	dispatchers: CoroutineDispatchers
) : BaseViewModel(dispatchers) {

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
	 * [MutableLiveData] holding [Event] signaling should loading be shown or hidden, exposed as [LiveData]
	 */
	private val _loading = MutableLiveData<Event<Boolean>>()
	val loading: LiveData<Event<Boolean>>
		get() = _loading

	/**
	 * Start fetching flow
	 */
	fun fetch(coinId: CoinId, coinsContext: CoinsContext = this.coinsContext) {
		_loading.value = Event(true)
		CoinUseCases.fetchCoinDetails(coinId).run(coinsContext).fix().value.unsafeRunAsync {
			_loading.value = Event(false)
			it.fold(_error::postValue, _coinDetails::postValue)
		}
	}
}