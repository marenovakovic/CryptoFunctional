package com.marko.cryptofunctional.dispatchers

import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class CoroutineDispatchersImpl @Inject constructor() : CoroutineDispatchers {

	override val main: CoroutineContext
		get() = Dispatchers.Main

	override val io: CoroutineContext
		get() = Dispatchers.IO

	override val async: CoroutineContext
		get() = Dispatchers.Default
}