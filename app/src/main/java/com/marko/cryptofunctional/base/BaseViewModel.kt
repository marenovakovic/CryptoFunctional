package com.marko.cryptofunctional.base

import androidx.lifecycle.ViewModel
import com.marko.cryptofunctional.data.coinsService
import com.marko.cryptofunctional.dispatchers.CoroutineDispatchers
import com.marko.cryptofunctional.injection.CoinsContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

/**
 * Base [ViewModel] class provideing [CoroutineScope]
 *
 * @param dispatchers [CoroutineDispatchers] abstraction
 */
abstract class BaseViewModel(
	private val dispatchers: CoroutineDispatchers
) : ViewModel(), CoroutineScope {

	/**
	 * Parent [Job]
	 */
	private val job = Job()

	/**
	 * [CoroutineContext]
	 * [CoroutineDispatchers.main] + [job]
	 */
	override val coroutineContext: CoroutineContext
		get() = dispatchers.main + job

	/**
	 * Dependencies needed for fetching [Coin]s
	 */
	protected val coinsContext = CoinsContext(scope = this, coinsService = coinsService)
}