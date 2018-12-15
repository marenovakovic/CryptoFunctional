package com.marko.cryptofunctional.dispatchers

import kotlin.coroutines.CoroutineContext

interface CoroutineDispatchers {

	val main: CoroutineContext

	val io: CoroutineContext

	val async: CoroutineContext
}