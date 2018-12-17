package com.marko.domain.usecases

import arrow.data.Reader
import arrow.data.ReaderApi
import arrow.data.map
import arrow.effects.DeferredK
import arrow.effects.unsafeRunAsync
import com.marko.domain.entities.CoinEntity
import com.marko.domain.injection.CoinsContext

/**
 * All [CoinEntity] related use cases
 */
object CoinUseCases {

	/**
	 * Use case fetching [CoinEntity] [List]
	 */
	fun fetchCoins(): Reader<CoinsContext, DeferredK<List<CoinEntity>>> =
		ReaderApi.ask<CoinsContext>().map { context ->
			DeferredK.async<List<CoinEntity>>(
				scope = context.scope,
				ctx = context.scope.coroutineContext
			) {
				context.coinsRepository.fetchCoins().unsafeRunAsync(it)
			}
		}
}