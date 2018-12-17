package com.marko.cryptofunctional.data

import arrow.Kind
import arrow.core.Either
import arrow.effects.typeclasses.Async
import com.marko.cryptofunctional.entities.Coin
import com.marko.cryptofunctional.entities.CoinId
import kotlinx.coroutines.runBlocking

/**
 * Data source for fetching [Coin] [List] from API
 */
interface CoinsRemoteSource<F> : Async<F> {

	/**
	 * [CoinsService] for communication with API
	 */
	val service: CoinsService

	/**
	 * Executes logic for fetching [Coin] [List]
	 *
	 * @return [Kind] of [F], [List] [Coin]
	 */
	fun fetchCoins(): Kind<F, List<Coin>> = async { callback ->
		runBlocking {
			try {
				val response = service.getCoins().await()
				callback(Either.right(response.coins))
			} catch (t: Throwable) {
				raiseError<F>(t)
			}
		}
	}

	/**
	 * Executes logic for fetching [Coin] details
	 *
	 * @param coinId [CoinId] of coin for which details should be fetched
	 *
	 * @return [Kind] of [F], [Coin]
	 */
	fun fetchCoinDetails(coinId: CoinId): Kind<F, Coin> = async { callback ->
		runBlocking {
			try {
				val response = service.getCoinDetails(coinId = coinId).await()
				callback(Either.right(response.coin))
			} catch (t: Throwable) {
				raiseError<F>(t)
			}
		}
	}
}