package com.marko.presentation.base

import androidx.lifecycle.ViewModel
import com.marko.domain.dispatchers.CoroutineDispatchers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

/**
 * Base [ViewModel] class providing [CoroutineScope]
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
	 * Cancel [job] because its not needed any more, currently makes no difference because of how app is written
	 */
	override fun onCleared() {
		super.onCleared()

		job.cancel()
	}
}