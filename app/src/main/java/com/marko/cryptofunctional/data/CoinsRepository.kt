package com.marko.cryptofunctional.data

import arrow.Kind
import arrow.effects.typeclasses.Async
import com.marko.cryptofunctional.entities.Coin
import com.marko.cryptofunctional.entities.CoinId
import kotlin.coroutines.CoroutineContext

/**
 * Interface combined from [CoinsRemoteSource], [CoinsCacheSource] and [Async]
 * that acts as a mediator between API and Cache fetching
 */
interface CoinsRepository<F> : CoinsRemoteSource<F>,
	CoinsCacheSource<F>, Async<F> {

	/**
	 * [CoroutineContext] in which fetching should be executed in
	 */
	val context: CoroutineContext

	/**
	 * Defer [Coin] [List] fetching from [CoinsRemoteSource] or [CoinsCacheSource]
	 *
	 * @return [defer] deferring fetching
	 */
	fun fetch(): Kind<F, List<Coin>> = defer(ctx = context) {
		fetchCoins().handleErrorWith { queryCoins() }
	}

	/**
	 * Defer [Coin] details fetching from [CoinsRemoteSource] or [CoinsCacheSource]
	 *
	 * @param coinId [CoinId] of [Coin] for which details should be fetched
	 *
	 * @return [defer] deferring fetching
	 */
	fun fetchDetails(coinId: CoinId): Kind<F, Coin> = defer(ctx = context) {
		fetchCoinDetails(coinId = coinId).handleErrorWith { queryCoinDetails(coinId = coinId) }
	}
}