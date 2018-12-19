package com.marko.remote.mappers

import com.marko.domain.entities.CoinEntity
import com.marko.remote.entities.CoinRemote
import com.marko.remote.factory.RemoteCoinFactory
import org.junit.jupiter.api.Test

internal class RemoteCoinMapperTest {

	@Test
	fun `test CoinEntity to CoinRemote mapping`() {
		val coinEntity = RemoteCoinFactory.createCoinEntity()
		val coinRemote = coinEntity.toRemote()

		assertCoins(coinEntity, coinRemote)
	}

	@Test
	fun `test CoinEntity list to CoinRemote list mapping`() {
		val coinEntities = RemoteCoinFactory.coinEntities
		val coinRemotes = coinEntities.toRemote()

		assert(coinEntities.size == coinRemotes.size)
		repeat(coinEntities.size) { assertCoins(coinEntities[it], coinRemotes[it]) }
	}

	@Test
	fun `test CoinRemote to CoinEntity mapping`() {
		val coinRemote = RemoteCoinFactory.createCoinRemote()
		val coinEntity = coinRemote.toEntity()

		assertCoins(coinEntity, coinRemote)
	}

	@Test
	fun `test CoinRemote list to CoinEntity list mapping`() {
		val coinRemotes = RemoteCoinFactory.coinRemotes
		val coinEntities = coinRemotes.toEntity()

		assert(coinRemotes.size == coinEntities.size)
		repeat(coinEntities.size) { assertCoins(coinEntities[it], coinRemotes[it]) }
	}

	private fun assertCoins(coinEntity: CoinEntity, coinRemote: CoinRemote) {
		assert(coinEntity.id == coinRemote.id)
		assert(coinEntity.name == coinRemote.name)
		assert(coinEntity.symbol == coinRemote.symbol)
		assert(coinEntity.price == coinRemote.price)
		assert(coinEntity.priceInBTC == coinRemote.priceInBTC)
		assert(coinEntity.inExistenceSupply == coinRemote.inExistenceSupply)
		assert(coinEntity.circulatingSupply == coinRemote.circulatingSupply)
		assert(coinEntity.maxSupply == coinRemote.maxSupply)
		assert(coinEntity.tradedIn24h == coinRemote.tradedIn24h)
	}
}