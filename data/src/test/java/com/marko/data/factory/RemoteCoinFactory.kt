package com.marko.data.factory

import com.marko.domain.entities.CoinEntity
import com.marko.remote.entities.CoinRemote

object RemoteCoinFactory {

	fun createCoinEntity(
		id: Int = 1,
		name: String = "Bitcoin",
		symbol: String = "BTC",
		price: Float = 0.0f,
		priceInBTC: Float = 0.0f,
		inExistenceSupply: Long = 1L,
		circulatingSupply: Long = 1L,
		maxSupply: Long = 1L,
		tradedIn24h: Long = 1L
	): CoinEntity = CoinEntity(
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

	val coinEntities: List<CoinEntity> = listOf(createCoinEntity(), createCoinEntity())

	fun createCoinRemote(
		id: Int = 1,
		name: String = "Bitcoin",
		symbol: String = "BTC",
		price: Float = 0.0f,
		priceInBTC: Float = 0.0f,
		inExistenceSupply: Long = 1L,
		circulatingSupply: Long = 1L,
		maxSupply: Long = 1L,
		tradedIn24h: Long = 1L
	): CoinRemote = CoinRemote(
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

	val coinRemotes: List<CoinRemote> = listOf(createCoinRemote(), createCoinRemote())
}