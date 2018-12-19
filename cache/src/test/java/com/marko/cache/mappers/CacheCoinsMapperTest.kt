package com.marko.cache.mappers

import com.marko.cache.entities.CoinCache
import com.marko.cache.factory.CacheCoinFactory
import com.marko.domain.entities.CoinEntity
import org.junit.jupiter.api.Test

internal class CacheCoinsMapperTest {

	@Test
	fun `test CoinEntity to CoinCache mapping`() {
		val coinEntity = CacheCoinFactory.createCoinEntity()
		val coinCache = coinEntity.toCache()

		assertCoins(coinEntity, coinCache)
	}

	@Test
	fun `test CoinEntity list to CoinCache list mapping`() {
		val coinEntities = CacheCoinFactory.coinEntities
		val coinCaches = coinEntities.toCache()

		assert(coinEntities.size == coinCaches.size)
		repeat(coinEntities.size) { assertCoins(coinEntities[it], coinCaches[it]) }
	}

	@Test
	fun `test CoinCache to CoinEntity mapping`() {
		val coinCache = CacheCoinFactory.createCoinCache()
		val coinEntity = coinCache.toEntity()

		assertCoins(coinEntity, coinCache)
	}

	@Test
	fun `test CoinCache list to CoinEntity list mapping`() {
		val coinCaches = CacheCoinFactory.coinCaches
		val coinEntities = coinCaches.toEntity()

		assert(coinEntities.size == coinCaches.size)
		repeat(coinEntities.size) { assertCoins(coinEntities[it], coinCaches[it]) }
	}

	private fun assertCoins(coinEntity: CoinEntity, coinCache: CoinCache) {
		assert(coinEntity.id == coinCache.id)
		assert(coinEntity.name == coinCache.name)
		assert(coinEntity.symbol == coinCache.symbol)
		assert(coinEntity.price == coinCache.price)
		assert(coinEntity.priceInBTC == coinCache.priceInBTC)
		assert(coinEntity.inExistenceSupply == coinCache.inExistenceSupply)
		assert(coinEntity.circulatingSupply == coinCache.circulatingSupply)
		assert(coinEntity.maxSupply == coinCache.maxSupply)
		assert(coinEntity.tradedIn24h == coinCache.tradedIn24h)
	}
}