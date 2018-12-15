package com.marko.cryptofunctional.domain

import arrow.data.Reader
import arrow.effects.DeferredK
import com.marko.cryptofunctional.data.Repository
import com.marko.cryptofunctional.entities.Coin
import com.marko.cryptofunctional.entities.CoinId
import com.marko.cryptofunctional.injection.CoinsContext

/**
 * All [Coin] related use cases
 */
object CoinUseCases {

	/**
	 * Use case for fetching [Coin]s
	 *
	 * @return [Reader] that knows how to fetch [Coin]s
	 */
	fun fetchCoins(): Reader<CoinsContext, DeferredK<List<Coin>>> = Repository.fetchCoins()

	/**
	 * Use case for fetching [Coin] details
	 *
	 * @return [Reader] that knows how to fetch [Coin] details
	 */
	fun fetchCoinDetails(coinId: CoinId): Reader<CoinsContext, DeferredK<Coin>> =
		Repository.fetchDetails(coinId)
}