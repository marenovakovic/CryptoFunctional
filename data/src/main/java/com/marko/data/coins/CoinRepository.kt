package com.marko.data.coins

import arrow.Kind
import com.marko.cache.coins.CoinsCacheSource
import com.marko.domain.entities.CoinEntity
import com.marko.domain.entities.CoinId
import com.marko.remote.coins.CoinsRemoteSource
import kotlin.coroutines.CoroutineContext

/**
 * Repository combining [CoinsRemoteSource] and [CoinsCacheSource]
 */
interface CoinRepository<F> : CoinsRemoteSource<F>, CoinsCacheSource<F> {

	/**
	 * [CoroutineContext] operations will be executed in
	 */
	val coroutineContext: CoroutineContext

	/**
	 * Defers executing of logic to mediate between [CoinsRemoteSource] and [CoinsCacheSource] for fetching of [CoinEntity] [List]
	 *
	 * @return [Kind] of [F], [List] [CoinEntity] supplied from data sources
	 */
	fun fetch(): Kind<F, List<CoinEntity>> = defer(ctx = coroutineContext) {
		fetchCoins().map { saveCoins(it); it }.handleErrorWith { queryCoins() }
	}

	/**
	 * Defers executing of logic to mediate between [CoinsRemoteSource] and [CoinsCacheSource] for fetching of [CoinEntity] details
	 *
	 * @param coinId [CoinId] of [CoinEntity] for which details should be fetched
	 *
	 * @return [Kind] of [F], [CoinEntity] supplied from data sources
	 */
	fun fetchCoin(coinId: CoinId): Kind<F, CoinEntity> = defer(ctx = coroutineContext) {
		fetchCoinDetails(coinId).handleErrorWith { querySingleCoin(coinId) }
	}
}