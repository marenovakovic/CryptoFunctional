package com.marko.usecases

import arrow.effects.DeferredK
import arrow.effects.ForDeferredK
import arrow.effects.fix
import com.marko.data.coins.CoinRepository
import com.marko.domain.entities.CoinEntity
import com.marko.domain.entities.CoinId

/**
 * Use case for fetching [CoinEntity] details from [CoinRepository]
 */
interface FetchCoinDetails {

	companion object {

		/**
		 * Executes logic for fetching [CoinEntity] details from [CoinRepository]
		 *
		 * @param coinRepository [CoinRepository]
		 *
		 * @param coinId [CoinId] of coin which should be fetched
		 *
		 * @return [DeferredK] containing [CoinEntity]
		 */
		operator fun invoke(
			coinRepository: CoinRepository<ForDeferredK>,
			coinId: CoinId
		): DeferredK<CoinEntity> =
			coinRepository.fetchCoin(coinId = coinId).fix()
	}
}