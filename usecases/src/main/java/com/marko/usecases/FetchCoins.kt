package com.marko.usecases

import arrow.effects.DeferredK
import arrow.effects.ForDeferredK
import arrow.effects.fix
import com.marko.data.coins.CoinRepository
import com.marko.domain.entities.CoinEntity

/**
 * Use case for fetching [CoinEntity] [List] from [CoinRepository]
 */
interface FetchCoins {

	companion object {

		/**
		 * Execute logic for fetching of [CoinEntity] [List] from [CoinRepository]
		 *
		 * @param coinRepository [CoinRepository]
		 *
		 * @return [DeferredK] containing [CoinEntity] [List]
		 */
		operator fun invoke(coinRepository: CoinRepository<ForDeferredK>): DeferredK<List<CoinEntity>> =
			coinRepository.fetch().fix()
	}
}