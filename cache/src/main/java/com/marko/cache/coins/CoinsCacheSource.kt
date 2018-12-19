package com.marko.cache.coins

import arrow.Kind
import arrow.core.Either
import arrow.core.Try
import arrow.effects.typeclasses.Async
import com.marko.cache.entities.CoinCache
import com.marko.cache.mappers.toCache
import com.marko.cache.mappers.toEntity
import com.marko.domain.entities.CoinEntity
import com.marko.domain.entities.CoinId

/**
 * DataSource for retrieving [CoinCache] from database
 */
interface CoinsCacheSource<F> : Async<F> {

	/**
	 * [CoinsDao] for interacting with the database
	 */
	val coinsDao: CoinsDao

	/**
	 * Executes [async] saving of [CoinCache] [List]
	 *
	 * @param coins [CoinEntity] [List] coins that should be saved into database
	 *
	 * @return [Kind] of [F], [Unit] containing query result
	 */
	fun saveCoins(coins: List<CoinEntity>): Kind<F, Unit> = async { callback ->
		Try { coinsDao.insertCoins(coins.toCache()) }.fold({ callback(Either.left(it)) }, { callback(Either.right(Unit)) })
	}

	/**
	 * Executes [async] saving of [CoinCache]
	 *
	 * @param coin [CoinEntity] coin that should be saved into database
	 *
	 * @return [Kind] of [F], [Unit] containing query result
	 */
	fun saveSingleCoin(coin: CoinEntity): Kind<F, Unit> = async { callback ->
		Try { coinsDao.insertCoin(coin.toCache()) }.fold({ callback(Either.left(it)) }, { callback(Either.right(Unit)) })
	}

	/**
	 * Executes [async] query for all saved [CoinCache] and returns result
	 *
	 * @return [Kind] of [F], [List] [CoinCache] containing query result
	 */
	fun queryCoins(): Kind<F, List<CoinEntity>> = async { callback ->
		Try { coinsDao.queryAllCoins() }.fold({ callback(Either.left(it)) }, { callback(Either.right(it.toEntity())) })
	}

	/**
	 * Executes [async] query for single [CoinCache] and returns result
	 *
	 * @param coinId [CoinId] of the [CoinCache] that should be queried from the database
	 *
	 * @return [Kind] of [F], [CoinCache] containing query result
	 */
	fun querySingleCoin(coinId: CoinId): Kind<F, CoinEntity> = async { callback ->
		Try { coinsDao.querySingleCoin(coinId = coinId) }.fold({ callback(Either.left(it)) }, { callback(Either.right(it.toEntity())) })
	}

	/**
	 * Executes [async] deleting of [CoinCache]s
	 *
	 * @param coins [CoinEntity] [List] coins that should be deleted from the database
	 *
	 * @return [Kind] of [F], [Unit] containing query result
	 */
	fun deleteCoins(coins: List<CoinEntity>): Kind<F, Unit> = async { callback ->
		Try { coinsDao.deleteCoins(coins = coins.toCache()) }.fold({ callback(Either.left(it)) }, { callback(Either.right(Unit)) })
	}
}