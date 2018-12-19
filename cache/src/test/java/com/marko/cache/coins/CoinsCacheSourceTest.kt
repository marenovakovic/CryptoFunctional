package com.marko.cache.coins

import arrow.effects.DeferredK
import arrow.effects.ForDeferredK
import arrow.effects.deferredk.async.async
import arrow.effects.fix
import arrow.effects.typeclasses.Async
import arrow.effects.unsafeRunSync
import com.marko.cache.entities.CoinCache
import com.marko.cache.factory.CacheCoinFactory
import com.marko.cache.mappers.toEntity
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class CoinsCacheSourceTest {

	private val coinsDao = mockk<CoinsDao>()

	@Test
	fun `test saveCoins success case`() {
		coinsDao.stubUnit()

		object : CoinsCacheSource<ForDeferredK>, Async<ForDeferredK> by DeferredK.async() {
			override val coinsDao: CoinsDao
				get() = this@CoinsCacheSourceTest.coinsDao
		}.saveCoins(listOf()).unsafeRunSync()
	}

	@Test
	fun `test saveCoins failure case`() {
		val throwableMessage = "jeb' se"
		val throwable = Throwable(throwableMessage)
		coinsDao.stubThrow(throwable)

		assertThrows<Throwable>(message = throwableMessage) {
			object : CoinsCacheSource<ForDeferredK>, Async<ForDeferredK> by DeferredK.async() {
				override val coinsDao: CoinsDao
					get() = this@CoinsCacheSourceTest.coinsDao
			}.saveCoins(listOf()).unsafeRunSync()
		}
	}

	@Test
	fun `test saveSingleCoins success case`() {
		coinsDao.stubUnit()

		object : CoinsCacheSource<ForDeferredK>, Async<ForDeferredK> by DeferredK.async() {
			override val coinsDao: CoinsDao
				get() = this@CoinsCacheSourceTest.coinsDao
		}.saveSingleCoin(CacheCoinFactory.createCoinEntity()).unsafeRunSync()
	}

	@Test
	fun `test saveSingleCoins failure case`() {
		val throwableMessage = "jeb' se"
		val throwable = Throwable(throwableMessage)
		coinsDao.stubThrow(throwable)

		assertThrows<Throwable>(message = throwableMessage) {
			object : CoinsCacheSource<ForDeferredK>, Async<ForDeferredK> by DeferredK.async() {
				override val coinsDao: CoinsDao
					get() = this@CoinsCacheSourceTest.coinsDao
			}.saveSingleCoin(CacheCoinFactory.createCoinEntity()).unsafeRunSync()
		}
	}

	@Test
	fun `test queryAllCoins success case`() {
		val coins = CacheCoinFactory.coinCaches
		coinsDao.stubCoins(coins)

		val result =
			object : CoinsCacheSource<ForDeferredK>, Async<ForDeferredK> by DeferredK.async() {
				override val coinsDao: CoinsDao
					get() = this@CoinsCacheSourceTest.coinsDao
			}.queryCoins().unsafeRunSync()

		assert(result == coins.toEntity())
	}

	@Test
	fun `test queryAllCoins failure case`() {
		val throwableMessage = "jeb' se"
		val throwable = Throwable(throwableMessage)
		coinsDao.stubThrow(throwable)

		assertThrows<Throwable>(message = throwableMessage) {
			object : CoinsCacheSource<ForDeferredK>, Async<ForDeferredK> by DeferredK.async() {
				override val coinsDao: CoinsDao
					get() = this@CoinsCacheSourceTest.coinsDao
			}.queryCoins().unsafeRunSync()
		}
	}

	@Test
	fun `test querySingleCoin success case`() {
		val coin = CacheCoinFactory.createCoinCache()
		coinsDao.stubCoin(coin)

		val result =
			object : CoinsCacheSource<ForDeferredK>, Async<ForDeferredK> by DeferredK.async() {
				override val coinsDao: CoinsDao
					get() = this@CoinsCacheSourceTest.coinsDao
			}.querySingleCoin(coin.id).fix().unsafeRunSync()

		assert(result == coin.toEntity())
	}

	@Test
	fun `test querySingleCoin failure case`() {
		val throwableMessage = "jeb' se"
		val throwable = Throwable(throwableMessage)
		coinsDao.stubThrow(throwable)

		assertThrows<Throwable>(message = throwableMessage) {
			object : CoinsCacheSource<ForDeferredK>, Async<ForDeferredK> by DeferredK.async() {
				override val coinsDao: CoinsDao
					get() = this@CoinsCacheSourceTest.coinsDao
			}.querySingleCoin(1).unsafeRunSync()
		}
	}

	@Test
	fun `test deleteCoins success case`() {
		coinsDao.stubUnit()

		object : CoinsCacheSource<ForDeferredK>, Async<ForDeferredK> by DeferredK.async() {
			override val coinsDao: CoinsDao
				get() = this@CoinsCacheSourceTest.coinsDao
		}.deleteCoins(listOf()).unsafeRunSync()
	}

	@Test
	fun `test deleteCoins failure case`() {
		val throwableMessage = "jeb' se"
		val throwable = Throwable(throwableMessage)
		coinsDao.stubThrow(throwable)

		assertThrows<Throwable>(message = throwableMessage) {
			object : CoinsCacheSource<ForDeferredK>, Async<ForDeferredK> by DeferredK.async() {
				override val coinsDao: CoinsDao
					get() = this@CoinsCacheSourceTest.coinsDao
			}.deleteCoins(listOf()).unsafeRunSync()
		}
	}

	private fun CoinsDao.stubCoins(coins: List<CoinCache>) {
		every { queryAllCoins() } returns coins
	}

	private fun CoinsDao.stubCoin(coin: CoinCache) {
		every { querySingleCoin(any()) } returns coin
	}

	private fun CoinsDao.stubUnit() {
		every { insertCoins(any()) } returns Unit
		every { insertCoin(any()) } returns Unit
		every { deleteCoins(any()) } returns Unit
	}

	private fun CoinsDao.stubThrow(throwable: Throwable) {
		every { queryAllCoins() } throws throwable
		every { querySingleCoin(any()) } throws throwable
		every { insertCoins(any()) } throws throwable
		every { insertCoin(any()) } throws throwable
		every { deleteCoins(any()) } throws throwable
	}
}