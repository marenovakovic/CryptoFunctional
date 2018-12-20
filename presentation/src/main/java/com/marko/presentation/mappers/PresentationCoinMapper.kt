package com.marko.presentation.mappers

import com.marko.domain.entities.CoinEntity
import com.marko.presentation.entities.Coin

/**
 * Mapping [CoinEntity] to [Coin]
 *
 * @receiver [CoinEntity] that is being mapped to [Coin]
 *
 * @return [Coin] mapped from [CoinEntity]
 */
fun CoinEntity.toPresentation(): Coin = Coin(
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
 * Mapping [CoinEntity] [List] to [Coin] [List]
 *
 * @receiver [CoinEntity] [List] that is being mapped to [Coin] [List]
 *
 * @return [Coin] [List] mapped from [CoinEntity] [List]
 */
fun List<CoinEntity>.toPresentation(): List<Coin> = map { it.toPresentation() }

/**
 * Mapping [Coin] to [CoinEntity]
 *
 * @receiver [Coin] that is being mapped to [CoinEntity]
 *
 * @return [CoinEntity] mapped from [Coin]
 */
fun Coin.toEntity(): CoinEntity = CoinEntity(
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
 * Mapping [Coin] [List] to [CoinEntity] [List]
 *
 * @receiver [Coin] [List] that is being mapped to [CoinEntity] [List]
 *
 * @return [CoinEntity] [List] mapped from [Coin] [List]
 */
fun List<Coin>.toEntity(): List<CoinEntity> = map { it.toEntity() }