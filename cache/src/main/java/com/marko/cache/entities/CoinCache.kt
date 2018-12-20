package com.marko.cache.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Cache layer representation of crypto currency
 */
@Entity(tableName = "coins")
data class CoinCache(
	/**
	 * Unique crypto currency id
	 */
	@PrimaryKey(autoGenerate = true) val id: Int,

	/**
	 * Crypto currency name
	 */
	val name: String,

	/**
	 * Crypto currency symbol
	 */
	val symbol: String,

	/**
	 * Logo image URL
	 */
	val logo: String,

	/**
	 * Crypto currency price in USD
	 */
	val price: Float,

	/**
	 * Crypto currency price in BTC (Bitcoin)
	 */
	@ColumnInfo(name = "btc_price") val priceInBTC: Float,

	/**
	 * Number of crypto currencies in existence
	 */
	@ColumnInfo(name = "in_existence_supply") val inExistenceSupply: Long,

	/**
	 * Number of crypto currencies currently in use (circulating in market)
	 */
	@ColumnInfo(name = "circulating_supply") val circulatingSupply: Long,

	/**
	 * Maximum number of crypto currency that will ever exist
	 */
	@ColumnInfo(name = "max_supply") val maxSupply: Long,

	/**
	 * Cumber of crypto currencies exchanged in 24 hours
	 */
	@ColumnInfo(name = "24h_trade") val tradedIn24h: Long
)