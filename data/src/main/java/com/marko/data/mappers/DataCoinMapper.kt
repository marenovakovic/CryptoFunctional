package com.marko.data.mappers

import com.marko.data.entities.CoinData
import com.marko.domain.entities.CoinEntity

/**
 * Mapping [CoinEntity] to [CoinData]
 *
 * @receiver [CoinEntity] that is being mapped to [CoinData]
 *
 * @return [CoinData] mapped from [CoinEntity]
 */
fun CoinEntity.toData(): CoinData = CoinData(
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

/**
 * Mapping [CoinEntity] [List] to [CoinData] [List]
 *
 * @receiver [CoinEntity] [List] that is being mapped to [CoinData] [List]
 *
 * @return [CoinData] [List] mapped from [CoinEntity] [List]
 */
fun List<CoinEntity>.toData(): List<CoinData> = map { it.toData() }

/**
 * Mapping [CoinData] to [CoinEntity]
 *
 * @receiver [CoinData] that is being mapped to [CoinEntity]
 *
 * @return [CoinEntity] mapped from [CoinData]
 */
fun CoinData.toEntity(): CoinEntity = CoinEntity(
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

/**
 * Mapping [CoinData] [List] to [CoinEntity] [List]
 *
 * @receiver [CoinData] [List] that is being mapped to [CoinEntity] [List]
 *
 * @return [CoinEntity] [List] mapped from [CoinData] [List]
 */
fun List<CoinData>.toEntity(): List<CoinEntity> = map { it.toEntity() }