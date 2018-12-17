package com.marko.cryptofunctional.tagles

import arrow.effects.DeferredK
import arrow.effects.ForDeferredK
import arrow.effects.deferredk.async.async
import arrow.effects.fix
import arrow.effects.typeclasses.Async
import arrow.effects.unsafeRunSync
import com.marko.cryptofunctional.data.CoinsRepository
import com.marko.cryptofunctional.data.CoinsService
import com.marko.cryptofunctional.entities.Coin
import com.marko.cryptofunctional.entities.CoinsMetadata
import com.marko.cryptofunctional.entities.CoinsResponse
import com.marko.cryptofunctional.factory.CoinsFactory
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

class TestCoinsRepository : StringSpec() {

	init {
		"test repository success case" {
			val context = Dispatchers.Unconfined
			val coinsService = mockk<CoinsService>()
			val coins = CoinsFactory.coins
			coinsService.stub(coins)

			val repository =
				object : CoinsRepository<ForDeferredK>, Async<ForDeferredK> by DeferredK.async() {
					override val context: CoroutineContext
						get() = context

					override val service: CoinsService
						get() = coinsService
				}

			val result = repository.fetch().fix().unsafeRunSync()

			result shouldBe coins
		}

		"test repository error case" {
			val context = Dispatchers.Unconfined
			val coinsService = mockk<CoinsService>()
			val throwable = Throwable("jeb' se")
			coinsService.stubThrow(throwable)

			val repository =
				object : CoinsRepository<ForDeferredK>, Async<ForDeferredK> by DeferredK.async() {
					override val context: CoroutineContext
						get() = context

					override val service: CoinsService
						get() = coinsService
				}

			val result = repository.fetch().fix().unsafeRunSync()

			result shouldBe emptyList()
		}
	}

	private fun CoinsService.stub(coins: List<Coin>) {

		val response = CoinsResponse(coins = coins, metadata = CoinsMetadata())

		every { this@stub.getCoins() } returns CompletableDeferred(response)
	}

	private fun CoinsService.stubThrow(throwable: Throwable) {
		every { this@stubThrow.getCoins() } throws throwable
	}
}