package com.marko.data.factory

import com.marko.cache.entities.CoinCache
import com.marko.domain.entities.CoinEntity

object CacheCoinFactory {

	fun createCoinEntity(
		id: Int = 1,
		name: String = "Bitcoin",
		symbol: String = "BTC",
		logo: String = "URL",
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
		logo = logo,
		price = price,
		priceInBTC = priceInBTC,
		inExistenceSupply = inExistenceSupply,
		circulatingSupply = circulatingSupply,
		maxSupply = maxSupply,
		tradedIn24h = tradedIn24h
	)

	val coinEntities: List<CoinEntity> = listOf(createCoinEntity(), createCoinEntity())

	fun createCoinCache(
		id: Int = 1,
		name: String = "Bitcoin",
		symbol: String = "BTC",
		logo: String = "URL",
		price: Float = 0.0f,
		priceInBTC: Float = 0.0f,
		inExistenceSupply: Long = 1L,
		circulatingSupply: Long = 1L,
		maxSupply: Long = 1L,
		tradedIn24h: Long = 1L
	): CoinCache = CoinCache(
		id = id,
		name = name,
		symbol = symbol,
		logo = logo,
		price = price,
		priceInBTC = priceInBTC,
		inExistenceSupply = inExistenceSupply,
		circulatingSupply = circulatingSupply,
		maxSupply = maxSupply,
		tradedIn24h = tradedIn24h
	)

	val coinCaches: List<CoinCache> = listOf(createCoinCache(), createCoinCache())
}