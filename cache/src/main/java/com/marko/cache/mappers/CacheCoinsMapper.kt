package com.marko.cache.mappers

import com.marko.cache.entities.CoinCache
import com.marko.domain.entities.CoinEntity

/**
 * Mapping [CoinEntity] to [CoinCache]
 *
 * @receiver [CoinEntity] that is being mapped to [CoinCache]
 *
 * @return [CoinCache] mapped from [CoinEntity]
 */
fun CoinEntity.toCache(): CoinCache = CoinCache(
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

/**
 * Mapping [CoinEntity] [List] to [CoinCache] [List]
 *
 * @receiver [CoinEntity] [List] that is being mapped to [CoinCache] [List]
 *
 * @return [CoinCache] [List] mapped from [CoinEntity] [List]
 */
fun List<CoinEntity>.toCache(): List<CoinCache> = map { it.toCache() }

/**
 * Mapping [CoinCache] to [CoinEntity]
 *
 * @receiver [CoinCache] that is being mapped to [CoinEntity]
 *
 * @return [CoinEntity] mapped from [CoinCache]
 */
fun CoinCache.toEntity(): CoinEntity = CoinEntity(
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

/**
 * Mapping [CoinCache] [List] to [CoinEntity] [List]
 *
 * @receiver [CoinCache] [List] that is being mapped to [CoinEntity] [List]
 *
 * @return [CoinEntity] [List] mapped from [CoinCache] [List]
 */
fun List<CoinCache>.toEntity(): List<CoinEntity> = map { it.toEntity() }