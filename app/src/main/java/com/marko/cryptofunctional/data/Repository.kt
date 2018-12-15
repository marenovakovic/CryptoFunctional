package com.marko.cryptofunctional.data

import arrow.data.Reader
import arrow.data.ReaderApi
import arrow.data.map
import arrow.effects.DeferredK
import arrow.effects.k
import arrow.effects.unsafeRunAsync
import com.marko.cryptofunctional.entities.Coin
import com.marko.cryptofunctional.entities.CoinId
import com.marko.cryptofunctional.injection.CoinsContext

/**
 * Repository for fetching [Coin] related content
 */
object Repository {

	/**
	 * Fetch [Coin]s from API
	 *
	 * @return [Reader] that knows how to fetch [Coin]s
	 */
	fun fetchCoins(): Reader<CoinsContext, DeferredK<List<Coin>>> =
		ReaderApi.ask<CoinsContext>().map { context ->
			DeferredK.async<List<Coin>>(scope = context.scope, ctx = context.scope.coroutineContext) {
				context.coinsService.getCoins().k().map { it.coins }.unsafeRunAsync(it)
			}
		}

	/**
	 * Fetch [Coin] details from API
	 *
	 * @param coinId [CoinId] id of wanted [Coin]
	 *
	 * @return [Reader] that knows how to fetch [Coin] details
	 */
	fun fetchDetails(coinId: CoinId): Reader<CoinsContext, DeferredK<Coin>> =
		ReaderApi.ask<CoinsContext>().map { context ->
			DeferredK.async<Coin>(scope = context.scope, ctx = context.scope.coroutineContext) {
				context.coinsService.getCoinDetails(coinId = coinId).k().map { it.coin }
					.unsafeRunAsync(it)
			}
		}
}