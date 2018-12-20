package com.marko.cache.coins

import androidx.room.*
import com.marko.cache.entities.CoinCache
import com.marko.domain.entities.CoinId

/**
 * [Dao] for interacting with database for [CoinCache] related actions
 */
@Dao
interface CoinsDao {

	/**
	 * Insert multiple [CoinCache] into the database
	 *
	 * @param coins [CoinCache] [List] to be inserted into the database
	 */
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	fun insertCoins(coins: List<CoinCache>)

	/**
	 * Insert single [CoinCache] into the database
	 *
	 * @param coin [CoinCache] to be inserted into the database
	 */
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	fun insertCoin(coin: CoinCache)

	/**
	 * Query all [CoinCache] from the database
	 */
	@Query("SELECT * FROM coins")
	fun queryAllCoins(): List<CoinCache>

	/**
	 * Query single [CoinCache] from the database
	 *
	 * @param coinId [CoinId] of [CoinCache] that should be retrieved from the database
	 */
	@Query("SELECT * FROM coins WHERE id = :coinId")
	fun querySingleCoin(coinId: CoinId): CoinCache

	/**
	 * Delete multiple [CoinCache] from the database
	 *
	 * @param coins [CoinCache] [List] to be deleted from the database
	 */
	@Delete
	fun deleteCoins(coins: List<CoinCache>)
}