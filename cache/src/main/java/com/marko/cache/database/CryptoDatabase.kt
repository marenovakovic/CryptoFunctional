package com.marko.cache.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.marko.cache.coins.CoinsDao
import com.marko.cache.entities.CoinCache

/**
 * [RoomDatabase] implementation
 */
@Database(entities = [CoinCache::class], version = 1)
abstract class CryptoDatabase : RoomDatabase() {

	/**
	 * Get access to [CoinsDao]
	 */
	abstract fun coinsDao(): CoinsDao
}