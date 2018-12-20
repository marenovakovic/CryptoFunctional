package com.marko.remote.mappers

import com.marko.domain.entities.CoinEntity
import com.marko.remote.entities.CoinRemote

/**
 * Mapping [CoinEntity] to [CoinRemote]
 *
 * @receiver [CoinEntity] that is being mapped to [CoinRemote]
 *
 * @return [CoinRemote] mapped from [CoinEntity]
 */
fun CoinEntity.toRemote(): CoinRemote = CoinRemote(
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
 * Mapping [CoinEntity] [List] to [CoinRemote] [List]
 *
 * @receiver [CoinEntity] [List] that is being mapped to [CoinRemote] [List]
 *
 * @return [CoinRemote] [List] mapped from [CoinEntity] [List]
 */
fun List<CoinEntity>.toRemote(): List<CoinRemote> = map { it.toRemote() }

/**
 * Mapping [CoinRemote] to [CoinEntity]
 *
 * @receiver [CoinRemote] that is being mapped to [CoinEntity]
 *
 * @return [CoinEntity] mapped from [CoinRemote]
 */
fun CoinRemote.toEntity(): CoinEntity = CoinEntity(
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
 * Mapping [CoinRemote] [List] to [CoinEntity] [List]
 *
 * @receiver [CoinRemote] [List] that is being mapped to [CoinEntity] [List]
 *
 * @return [CoinEntity] [List] mapped from [CoinRemote] [List]
 */
fun List<CoinRemote>.toEntity(): List<CoinEntity> = map { it.toEntity() }