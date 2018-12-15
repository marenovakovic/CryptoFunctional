package com.marko.cryptofunctional.extensions

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

fun <T> LifecycleOwner.observeNonNull(liveData: LiveData<T>, observer: (T) -> Unit) {
	liveData.observe(this, Observer { it?.let(observer) })
}

fun <T> LiveData<T>.observeNonNull(owner: LifecycleOwner, observer: (T) -> Unit) =
	this.observe(owner, Observer { it?.let(observer) })