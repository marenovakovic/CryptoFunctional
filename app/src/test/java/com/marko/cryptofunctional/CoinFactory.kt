package com.marko.cryptofunctional

import com.marko.presentation.entities.Coin

object CoinFactory {

	fun createCoin(
		id: Int = 1,
		name: String = "Bitcoin",
		symbol: String = "BTC",
		price: Float = 0.0f,
		priceInBTC: Float = 0.0f,
		inExistenceSupply: Long = 1L,
		circulatingSupply: Long = 1L,
		maxSupply: Long = 1L,
		tradedIn24h: Long = 1L
	): Coin = Coin(
		id = id,
		name = name,
		symbol = symbol,
		price = price,
		priceInBTC = priceInBTC,
		inExistenceSupply = inExistenceSupply,
		circulatingSupply = circulatingSupply,
		maxSupply = maxSupply,
		tradedIn24h = tradedIn24h
	)
}