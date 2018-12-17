package com.marko.cryptofunctional.usecases

import arrow.core.Either
import arrow.effects.DeferredK
import arrow.effects.ForDeferredK
import arrow.effects.deferredk.async.async
import arrow.effects.fix
import arrow.effects.typeclasses.Async
import arrow.effects.unsafeRunAsync
import com.marko.cryptofunctional.data.CoinsService
import com.marko.cryptofunctional.entities.Coin
import com.marko.cryptofunctional.data.CoinsRepository
import kotlin.coroutines.CoroutineContext

/**
 * Executes business logic for fetching [Coin] [List]
 */
interface FetchCoins {

	companion object {
		/**
		 * Here business logic for fetching is executed
		 *
		 * @param context [CoroutineContext] in which fetching is done
		 *
		 * @param service [CoinsService] for interacting with API
		 *
		 * @param callback callback function that takes [Either] of [Throwable], [List] [Coin]
		 */
		operator fun invoke(
			context: CoroutineContext,
			service: CoinsService,
			callback: (Either<Throwable, List<Coin>>) -> Unit
		) {
			object : CoinsRepository<ForDeferredK>, Async<ForDeferredK> by DeferredK.async() {
				override val context: CoroutineContext
					get() = context

				override val service: CoinsService
					get() = service
			}.fetchCoins().fix().unsafeRunAsync(callback)
		}
	}
}