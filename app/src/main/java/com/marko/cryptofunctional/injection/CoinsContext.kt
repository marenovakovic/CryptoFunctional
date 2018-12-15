package com.marko.cryptofunctional.injection

import com.marko.cryptofunctional.data.CoinsService
import kotlinx.coroutines.CoroutineScope

/**
 * Dependencies needed in order to fetch [Coin]s from API
 *
 * @param scope [CoroutineScope] in which fetching should be done
 *
 * @param coinsService [CoinsService] that is used to fetch [Coin]s
 */
data class CoinsContext(
	val scope: CoroutineScope,
	val coinsService: CoinsService
)