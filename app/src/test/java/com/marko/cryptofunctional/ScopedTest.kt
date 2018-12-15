package com.marko.cryptofunctional

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

/**
 * Test base class that provides [CoroutineScope]
 */
abstract class ScopedTest : CoroutineScope {

	/**
	 * Parent test [Job]
	 */
	private val job = Job()

	/**
	 * [CoroutineContext] defined for [this] [CoroutineScope]
	 * [Dispatchers.Unconfined] + [job]
	 */
	override val coroutineContext: CoroutineContext
		get() = Dispatchers.Unconfined + job
}