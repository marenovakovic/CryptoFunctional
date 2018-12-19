package com.marko.remote.coins

import arrow.Kind
import arrow.core.Either
import arrow.core.Try
import arrow.effects.typeclasses.Async
import com.marko.domain.entities.CoinEntity
import com.marko.domain.entities.CoinId
import com.marko.remote.mappers.toEntity
import kotlinx.coroutines.runBlocking

/**
 * DataSource fetching [CoinRemote] [List] from API
 */
interface CoinsRemoteSource<F> : Async<F> {

	/**
	 * [CoinsService] for interacting with API
	 */
	val service: CoinsService

	/**
	 * Executes [async] fetching logic
	 *
	 * @return [Kind] of [F], [CoinEntity] [List]
	 */
	fun fetchCoins(): Kind<F, List<CoinEntity>> = async { callback ->
		runBlocking {
			Try { service.getCoins().await().coins.toEntity() }
				.fold({ it.printStackTrace(); callback(Either.left(it)) }, { callback(Either.right(it)) })
		}
	}

	/**
	 * Executes [async] coin details fetching logic
	 *
	 * @param coinId [CoinId] of [CoinEntity] for which details should be fetched
	 *
	 * @return [Kind] of [F], [CoinEntity]
	 */
	fun fetchCoinDetails(coinId: CoinId): Kind<F, CoinEntity> = async { callback ->
		runBlocking {
			Try { service.getCoinDetails(coinId = coinId).await().coin.toEntity() }
				.fold({ callback(Either.left(it)) }, { callback(Either.right(it)) })
		}
	}
}