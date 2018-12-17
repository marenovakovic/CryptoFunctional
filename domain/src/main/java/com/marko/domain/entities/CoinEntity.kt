package com.marko.domain.entities

typealias CoinId = Int

/**
 * Domain layer coin representation
 */
data class CoinEntity(
	/**
	 * Unique identifier for each coin
	 */
	val id: Int,

	/**
	 * Name of the coin
	 */
	val name: String,

	/**
	 * Symbol of the coin
	 */
	val symbol: String
)