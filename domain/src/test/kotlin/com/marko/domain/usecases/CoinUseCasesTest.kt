package com.marko.domain.usecases

import arrow.core.fix
import arrow.core.right
import arrow.effects.DeferredK
import arrow.effects.unsafeRunAsync
import arrow.effects.unsafeRunSync
import com.marko.domain.coins.CoinsRepository
import com.marko.domain.entities.CoinEntity
import com.marko.domain.factory.DomainCoinsFactory
import com.marko.domain.injection.CoinsContext
import io.kotlintest.Description
import io.kotlintest.Spec
import io.kotlintest.assertions.arrow.either.shouldBeLeft
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

internal class CoinUseCasesTest : StringSpec() {

	private val scope = mockk<CoroutineScope>()
	private val coinsRepository = mockk<CoinsRepository>()
	private val context = CoinsContext(scope = scope, coinsRepository = coinsRepository)

	override fun beforeSpec(description: Description, spec: Spec) {
		stubContext()
	}

	init {
		"does fetchCoins use case call repository" {
			val coins = DomainCoinsFactory.coinEntities
			stubCoins(coins)

			CoinUseCases.fetchCoins().run(context).fix().value.unsafeRunSync()

			verify(exactly = 1) { coinsRepository.fetchCoins() }
		}

		"check fetchCoins use case result" {
			val coins = DomainCoinsFactory.coinEntities
			stubCoins(coins)

			val result = CoinUseCases.fetchCoins().run(context).fix().value.unsafeRunSync()

			result shouldBe coins
		}

		"check fetchCoins result when exception is thrown" {
			val throwable = Throwable("jeb' se")
			stubException(throwable)

			CoinUseCases.fetchCoins().run(context).fix().value.unsafeRunAsync {
				it.shouldBeLeft(throwable)
			}
		}
	}

	private fun stubCoins(coins: List<CoinEntity>) {
		every { coinsRepository.fetchCoins() } returns DeferredK.just(coins)
	}

	private fun stubException(throwable: Throwable) {
		every { coinsRepository.fetchCoins() } throws throwable
	}

	private fun stubContext() {
		every { scope.coroutineContext } returns Dispatchers.Unconfined
	}
}

