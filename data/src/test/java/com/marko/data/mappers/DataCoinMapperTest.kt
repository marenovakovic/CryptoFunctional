package com.marko.data.mappers

import com.marko.data.entities.CoinData
import com.marko.data.factory.DataCoinFactory
import com.marko.domain.entities.CoinEntity
import org.junit.jupiter.api.Test

internal class DataCoinMapperTest {

	@Test
	fun `test CoinEntity to CoinData mapping`() {
		val coinEntity = DataCoinFactory.createCoinEntity()
		val coinData = coinEntity.toData()

		assertCoins(coinEntity, coinData)
	}

	@Test
	fun `test CoinEntity list to CoinData list mapping`() {
		val coinEntities = DataCoinFactory.coinEntities
		val coinDatas = coinEntities.toData()

		assert(coinEntities.size == coinDatas.size)
		repeat(coinEntities.size) { assertCoins(coinEntities[it], coinDatas[it]) }
	}

	@Test
	fun `test CoinData to CoinEntity mapping`() {
		val coinData = DataCoinFactory.createCoinData()
		val coinEntity = coinData.toEntity()

		assertCoins(coinEntity, coinData)
	}

	@Test
	fun `test CoinData list to CoinEntity list mapping`() {
		val coinDatas = DataCoinFactory.coinDatas
		val coinEntities = coinDatas.toEntity()

		assert(coinDatas.size == coinEntities.size)
		repeat(coinDatas.size) { assertCoins(coinEntities[it], coinDatas[it]) }
	}

	private fun assertCoins(coinEntity: CoinEntity, coinData: CoinData) {
		assert(coinEntity.id == coinData.id)
		assert(coinEntity.name == coinData.name)
		assert(coinEntity.symbol == coinData.symbol)
		assert(coinEntity.price == coinData.price)
		assert(coinEntity.priceInBTC == coinData.priceInBTC)
		assert(coinEntity.inExistenceSupply == coinData.inExistenceSupply)
		assert(coinEntity.circulatingSupply == coinData.circulatingSupply)
		assert(coinEntity.maxSupply == coinData.maxSupply)
		assert(coinEntity.tradedIn24h == coinData.tradedIn24h)
	}
}