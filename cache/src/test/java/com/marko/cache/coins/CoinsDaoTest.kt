package com.marko.cache.coins

import org.junit.Test

internal class CoinsDaoTest : DatabaseTest() {

	@Test
	fun `is inserting coins done without errors`() {
		coinsDao.insertCoins(coinCaches)
	}

	@Test
	fun `does inserting coins works correctly`() {
		coinsDao.insertCoins(coinCaches)

		val savedCoins = coinsDao.queryAllCoins()

		assert(coinCaches == savedCoins)
	}

	@Test
	fun `does inserting single coin works correctly`() {
		coinsDao.insertCoin(coinCacheOne)

		val coin = coinsDao.querySingleCoin(coinCacheOne.id)

		assert(coin == coinCacheOne)
	}

	@Test
	fun `does deleting coins works correctly`() {
		coinsDao.insertCoins(coinCaches)

		val savedCoins = coinsDao.queryAllCoins()
		assert(savedCoins.isNotEmpty())
		assert(savedCoins == coinCaches)

		coinsDao.deleteCoins(coinCaches)

		val coins = coinsDao.queryAllCoins()
		assert(coins.isEmpty())
	}
}