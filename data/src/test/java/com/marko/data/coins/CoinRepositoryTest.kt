package com.marko.data.coins

import arrow.effects.fix
import arrow.effects.unsafeRunSync
import com.marko.cache.mappers.toEntity
import com.marko.data.factory.CacheCoinFactory
import com.marko.data.factory.RemoteCoinFactory
import com.marko.remote.mappers.toEntity
import io.mockk.verify
import org.junit.jupiter.api.Test

internal class CoinRepositoryTest : RepositoryTest() {

	@Test
	fun `check does fetchCoins returns correct result when no exceptions occur`() {
		val coins = RemoteCoinFactory.coinRemotes
		coinsService.stubCoins(coins)

		val result = repository.fetch().fix().unsafeRunSync()

		verify(exactly = 1) { coinsService.getCoins() }
		verify(exactly = 0) { coinsDao.queryAllCoins() }
		assert(result == coins.toEntity())
	}

	@Test
	fun `check does fetchCoins returns correct result when exceptions do occur`() {
		val throwable = Throwable("jeb' se")
		coinsService.stubThrow(throwable)

		val coins = CacheCoinFactory.coinCaches
		coinsDao.stubCoins(coins)

		val result = repository.fetch().fix().unsafeRunSync()

		verify(exactly = 1) { coinsService.getCoins() }
		verify(exactly = 1) { coinsDao.queryAllCoins() }
		assert(result == coins.toEntity())
	}

	@Test
	fun `check does fetchCoin return correct result when no exception occur`() {
		val coin = RemoteCoinFactory.createCoinRemote()
		coinsService.stubCoin(coin)

		val coinId = 1

		val result = repository.fetchCoin(coinId).fix().unsafeRunSync()

		verify(exactly = 1) { coinsService.getCoinDetails(coinId = coinId) }
		verify(exactly = 0) { coinsDao.querySingleCoin(coinId) }
		assert(result == coin.toEntity())
	}

	@Test
	fun `check does fetchCoin return correct result when exception do occur`() {
		val throwable = Throwable("jeb' se")
		coinsService.stubThrow(throwable)

		val coin = CacheCoinFactory.createCoinCache()
		coinsDao.stubCoin(coin)

		val coinId = 1

		val result = repository.fetchCoin(coinId).fix().unsafeRunSync()

		verify(exactly = 1) { coinsService.getCoinDetails(coinId = coinId) }
		verify(exactly = 1) { coinsDao.querySingleCoin(coinId) }
		assert(result == coin.toEntity())
	}
}