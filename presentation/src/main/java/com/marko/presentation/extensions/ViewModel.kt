package com.marko.presentation.extensions

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import arrow.core.Either
import arrow.effects.DeferredK
import arrow.effects.unsafeRunAsync
import com.marko.presentation.event.Event

fun <A> ViewModel.executeWithLoading(
	loading: MutableLiveData<Event<Boolean>>,
	success: MutableLiveData<A>,
	error: MutableLiveData<Throwable>,
	deferred: DeferredK<A>
) {
	loading.value = Event(true)

	deferred.unsafeRunAsync {
		loading.postValue(Event(false))
		it.fold(error::postValue, success::postValue)
	}
}