package com.marko.remote.coins

import arrow.effects.DeferredK
import arrow.effects.ForDeferredK
import arrow.effects.deferredk.async.async
import arrow.effects.fix
import arrow.effects.typeclasses.Async
import arrow.effects.unsafeRunSync
import com.marko.remote.entities.CoinRemote
import com.marko.remote.entities.CoinResponse
import com.marko.remote.entities.CoinStatus
import com.marko.remote.entities.CoinsResponse
import com.marko.remote.factory.RemoteCoinFactory
import com.marko.remote.mappers.toEntity
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.CompletableDeferred
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class CoinsRemoteSourceTest {

	private val coinsService = mockk<CoinsService>()

	@Test
	fun `test fetchCoins success case`() {
		val coins = RemoteCoinFactory.coinRemotes
		coinsService.stubCoins(coins)

		val result =
			object : CoinsRemoteSource<ForDeferredK>, Async<ForDeferredK> by DeferredK.async() {
				override val service: CoinsService
					get() = coinsService
			}.fetchCoins().fix().unsafeRunSync()

		assert(result == coins.toEntity())
	}

	@Test
	fun `test fetchCoins failure case`() {
		val throwableMessage = "jeb' se"
		val throwable = Throwable(throwableMessage)
		coinsService.stubThrow(throwable)

		assertThrows<Throwable>(message = throwableMessage) {
			object : CoinsRemoteSource<ForDeferredK>, Async<ForDeferredK> by DeferredK.async() {
				override val service: CoinsService
					get() = coinsService
			}.fetchCoins().fix().unsafeRunSync()
		}
	}

	@Test
	fun `test fetchCoinDetails success case`() {
		val coin = RemoteCoinFactory.createCoinRemote()
		coinsService.stubCoin(coin)

		val coinId = 1

		val result =
			object : CoinsRemoteSource<ForDeferredK>, Async<ForDeferredK> by DeferredK.async() {
				override val service: CoinsService
					get() = coinsService
			}.fetchCoinDetails(coinId).fix().unsafeRunSync()

		verify(exactly = 1) { coinsService.getCoinDetails(coinId = coinId) }
		assert(result == coin.toEntity())
	}

	@Test
	fun `test fetchCoinDetails failure case`() {
		val throwableMessage = "jeb' se"
		val throwable = Throwable(throwableMessage)
		coinsService.stubThrow(throwable)

		val coinId = 1

		assertThrows<Throwable> {
			object : CoinsRemoteSource<ForDeferredK>, Async<ForDeferredK> by DeferredK.async() {
				override val service: CoinsService
					get() = coinsService
			}.fetchCoinDetails(coinId).fix().unsafeRunSync()
		}

		verify(exactly = 1) { coinsService.getCoinDetails(coinId = coinId) }
	}

	private fun CoinsService.stubCoins(coins: List<CoinRemote>) {
		val response = CoinsResponse(coins = coins, status = CoinStatus())
		every { getCoins() } returns CompletableDeferred(response)
	}

	protected fun CoinsService.stubCoin(coin: CoinRemote) {
		val response = CoinResponse(coin = coin, status = CoinStatus())
		every { getCoinDetails(any(), any()) } returns CompletableDeferred(response)
	}

	private fun CoinsService.stubThrow(throwable: Throwable) {
		every { getCoins() } throws throwable
		every { getCoinDetails(any(), any()) } throws throwable
	}
}