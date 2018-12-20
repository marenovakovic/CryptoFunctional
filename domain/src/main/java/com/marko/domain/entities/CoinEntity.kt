package com.marko.domain.entities

typealias CoinId = Int

/**
 * Domain layer representation of crypto currency
 */
data class CoinEntity(
	/**
	 * Unique crypto currency id
	 */
	val id: Int,

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
	val priceInBTC: Float,

	/**
	 * Number of crypto currencies in existence
	 */
	val inExistenceSupply: Long,

	/**
	 * Number of crypto currencies currently in use (circulating in market)
	 */
	val circulatingSupply: Long,

	/**
	 * Maximum number of crypto currency that will ever exist
	 */
	val maxSupply: Long,

	/**
	 * Cumber of crypto currencies exchanged in 24 hours
	 */
	val tradedIn24h: Long
)