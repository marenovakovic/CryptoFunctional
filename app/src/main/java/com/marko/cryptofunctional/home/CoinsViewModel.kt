package com.marko.cryptofunctional.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import arrow.core.fix
import arrow.effects.unsafeRunAsync
import com.marko.cryptofunctional.base.BaseViewModel
import com.marko.cryptofunctional.dispatchers.CoroutineDispatchers
import com.marko.cryptofunctional.domain.CoinUseCases
import com.marko.cryptofunctional.entities.Coin
import com.marko.cryptofunctional.event.Event
import com.marko.cryptofunctional.injection.CoinsContext
import javax.inject.Inject

/**
 * [ViewModel] class for fetching [Coin]s
 *
 * @param dispatchers [CoroutineDispatchers] abstraction
 */
class CoinsViewModel @Inject constructor(
	dispatchers: CoroutineDispatchers
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
	fun fetch(coinsContext: CoinsContext = this.coinsContext) {
		_loading.value = Event(true)
		CoinUseCases.fetchCoins().run(coinsContext).fix().value.unsafeRunAsync {
			_loading.value = Event(false)
			it.fold(_error::postValue, _coins::postValue)
		}
	}
}