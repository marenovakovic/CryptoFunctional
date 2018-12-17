package com.marko.domain.coins

import arrow.effects.DeferredK
import com.marko.domain.entities.CoinEntity

interface CoinsRepository {

	fun fetchCoins(): DeferredK<List<CoinEntity>>
}