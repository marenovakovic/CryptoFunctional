package com.marko.presentation.coindetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import arrow.effects.ForDeferredK
import com.marko.data.coins.CoinRepository
import com.marko.domain.dispatchers.CoroutineDispatchers
import com.marko.domain.entities.CoinId
import com.marko.presentation.base.BaseViewModel
import com.marko.presentation.entities.Coin
import com.marko.presentation.event.Event
import com.marko.presentation.extensions.executeWithLoading
import com.marko.presentation.mappers.toPresentation
import com.marko.usecases.FetchCoinDetails
import javax.inject.Inject

/**
 * [ViewModel] class for fetching [Coin] details
 *
 * @param dispatchers [CoroutineDispatchers]
 */
class CoinDetailsViewModel @Inject constructor(
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
	 * [MutableLiveData] holding fetched [Coin], exposed as [LiveData]
	 */
	private val _coinDetails = MutableLiveData<Coin>()
	val coinDetails: LiveData<Coin>
		get() = _coinDetails

	/**
	 * [MutableLiveData] holding [Throwable] thrown during fetching, exposed as [LiveData]
	 */
	private val _error = MutableLiveData<Throwable>()
	val error: LiveData<Throwable>
		get() = _error

	/**
	 * Start fetching flow
	 *
	 * @param coinId [CoinId] of [Coin] that should be fetched
	 */
	fun fetch(coinId: CoinId) = executeWithLoading(
		loading = _loading,
		success = _coinDetails,
		error = _error,
		deferred = FetchCoinDetails(
			coinRepository = coinRepository,
			coinId = coinId
		).map { it.toPresentation() }
	)
}