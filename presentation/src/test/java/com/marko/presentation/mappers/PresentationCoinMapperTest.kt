package com.marko.presentation.mappers

import com.marko.domain.entities.CoinEntity
import com.marko.presentation.PresentationCoinsFactory
import com.marko.presentation.entities.Coin
import org.junit.jupiter.api.Test

internal class PresentationCoinMapperTest {

	@Test
	fun `test CoinEntity to CoinPresentation mapping`() {
		val coinEntity = PresentationCoinsFactory.createCoinEntity()
		val coinPresentation = coinEntity.toPresentation()

		assertCoins(coinEntity, coinPresentation)
	}

	@Test
	fun `test CoinEntity list to CoinPresentation list mapping`() {
		val coinEntities = PresentationCoinsFactory.coinEntities
		val coinPresentations = coinEntities.toPresentation()

		assert(coinEntities.size == coinPresentations.size)
		repeat(coinEntities.size) { assertCoins(coinEntities[it], coinPresentations[it]) }
	}

	@Test
	fun `test CoinPresentation to CoinEntity mapping`() {
		val coinPresentation = PresentationCoinsFactory.createCoinPresentation()
		val coinEntity = coinPresentation.toEntity()

		assertCoins(coinEntity, coinPresentation)
	}

	@Test
	fun `test CoinPresentation list to CoinEntity list mapping`() {
		val coinPresentations = PresentationCoinsFactory.COINS
		val coinEntities = coinPresentations.toEntity()

		assert(coinPresentations.size == coinEntities.size)
		repeat(coinEntities.size) { assertCoins(coinEntities[it], coinPresentations[it]) }
	}

	private fun assertCoins(coinEntity: CoinEntity, coin: Coin) {
		assert(coinEntity.id == coin.id)
		assert(coinEntity.name == coin.name)
		assert(coinEntity.symbol == coin.symbol)
		assert(coinEntity.price == coin.price)
		assert(coinEntity.priceInBTC == coin.priceInBTC)
		assert(coinEntity.inExistenceSupply == coin.inExistenceSupply)
		assert(coinEntity.circulatingSupply == coin.circulatingSupply)
		assert(coinEntity.maxSupply == coin.maxSupply)
		assert(coinEntity.tradedIn24h == coin.tradedIn24h)
	}
}