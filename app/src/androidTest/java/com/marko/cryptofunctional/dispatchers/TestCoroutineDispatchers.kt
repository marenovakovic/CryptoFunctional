package com.marko.cryptofunctional.dispatchers

import com.marko.domain.dispatchers.CoroutineDispatchers
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class TestCoroutineDispatchersInstrumentation @Inject constructor() : CoroutineDispatchers {
	override val main: CoroutineContext
		get() = Dispatchers.Unconfined

	override val io: CoroutineContext
		get() = Dispatchers.Unconfined

	override val async: CoroutineContext
		get() = Dispatchers.Unconfined
}