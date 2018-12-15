package com.marko.cryptofunctional.base

import dagger.android.support.DaggerAppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

abstract class BaseActivity : DaggerAppCompatActivity(), CoroutineScope {

	private val job = Job()

	final override val coroutineContext: CoroutineContext
		get() = Dispatchers.IO + job
}