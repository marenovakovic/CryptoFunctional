package com.marko.presentation.dispatchers

import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

class TestCoroutineDispatchers : CoroutineDispatchers {
	override val main: CoroutineContext
		get() = Dispatchers.Unconfined

	override val io: CoroutineContext
		get() = Dispatchers.Unconfined

	override val async: CoroutineContext
		get() = Dispatchers.Unconfined
}