package com.marko.cryptofunctional.factory

import com.marko.cryptofunctional.entities.Coin

object CoinsFactory {

	fun createCoin(
		id: Int = 1,
		name: String = "Bitcoin",
		symbol: String = "BTC"
	) : Coin = Coin(id = id, name = name, symbol = symbol)

	val coins = listOf(createCoin(), createCoin(), createCoin())
}