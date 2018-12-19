package com.marko.cryptofunctional.base

import dagger.android.support.DaggerAppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

/**
 * Base [Activity] class providing [CoroutineScope]
 */
abstract class BaseActivity : DaggerAppCompatActivity(), CoroutineScope {

	/**
	 * Parent [Job]
	 */
	private val job = Job()

	/**
	 * [BaseActivity] [CoroutineContext], [Dispatchers.Main] + [job]
	 */
	final override val coroutineContext: CoroutineContext
		get() = Dispatchers.Main + job

	/**
	 * Cancel [job] and all it's children because it's needed no longer
	 */
	override fun onDestroy() {
		super.onDestroy()

		job.cancel()
	}
}