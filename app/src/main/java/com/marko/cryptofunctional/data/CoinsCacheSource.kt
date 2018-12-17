package com.marko.cryptofunctional.data

import arrow.Kind
import arrow.core.Either
import arrow.effects.typeclasses.Async
import com.marko.cryptofunctional.entities.Coin
import com.marko.cryptofunctional.entities.CoinId

interface CoinsCacheSource<F> : Async<F> {

	fun queryCoins(): Kind<F, List<Coin>> = async { it(Either.right(listOf())) }

	fun queryCoinDetails(coinId: CoinId): Kind<F, Coin> = async {
		it(Either.right(
			Coin(
				id = 1,
				name = "Bitcoin",
				symbol = "BTC"
			)
		))
	}
}