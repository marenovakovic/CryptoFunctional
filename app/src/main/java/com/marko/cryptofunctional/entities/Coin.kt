package com.marko.cryptofunctional.entities

typealias CoinId = Int

/**
 * Representation of coin
 */
data class Coin(
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