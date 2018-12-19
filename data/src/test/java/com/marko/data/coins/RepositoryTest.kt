package com.marko.data.coins

import arrow.effects.DeferredK
import arrow.effects.ForDeferredK
import arrow.effects.deferredk.async.async
import arrow.effects.typeclasses.Async
import com.marko.cache.coins.CoinsDao
import com.marko.cache.entities.CoinCache
import com.marko.domain.entities.CoinEntity
import com.marko.remote.coins.CoinsService
import com.marko.remote.entities.CoinRemote
import com.marko.remote.entities.CoinResponse
import com.marko.remote.entities.CoinStatus
import com.marko.remote.entities.CoinsResponse
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

abstract class RepositoryTest() {

	protected val coinsService = mockk<CoinsService>()
	protected val coinsDao = mockk<CoinsDao>()

	protected val repository: CoinRepository<ForDeferredK> =
		object : CoinRepository<ForDeferredK>, Async<ForDeferredK> by DeferredK.async() {
			override val coroutineContext: CoroutineContext
				get() = Dispatchers.Unconfined

			override val service: CoinsService
				get() = coinsService

			override val coinsDao: CoinsDao
				get() = this@RepositoryTest.coinsDao
		}

	protected fun CoinsService.stubCoins(coins: List<CoinRemote>) {
		val response = CoinsResponse(coins = coins, status = CoinStatus())
		every { getCoins() } returns CompletableDeferred(response)
	}

	protected fun CoinsService.stubCoin(coin: CoinRemote) {
		val response = CoinResponse(coin = coin, status = CoinStatus())
		every { getCoinDetails(any(), any()) } returns CompletableDeferred(response)
	}

	protected fun CoinsService.stubThrow(throwable: Throwable) {
		every { getCoins() } throws throwable
	}

	protected fun CoinsDao.stubCoins(coins: List<CoinCache>) {
		every { queryAllCoins() } returns coins
	}

	protected fun CoinsDao.stubCoin(coin: CoinCache) {
		every { querySingleCoin(any()) } returns coin
	}

	protected fun CoinsDao.stubUnit() {
		every { insertCoins(any()) } returns Unit
		every { insertCoin(any()) } returns Unit
		every { deleteCoins(any()) } returns Unit
	}

	protected fun CoinsDao.stubThrow(throwable: Throwable) {
		every { queryAllCoins() } throws throwable
		every { querySingleCoin(any()) } throws throwable
		every { insertCoins(any()) } throws throwable
		every { insertCoin(any()) } throws throwable
		every { deleteCoins(any()) } throws throwable
	}
}