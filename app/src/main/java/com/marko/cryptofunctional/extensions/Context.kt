package com.marko.cryptofunctional.extensions

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.Toast

fun Context.toastShort(message: String?) =
	Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

fun Context.toastLong(message: String?) =
	Toast.makeText(this, message, Toast.LENGTH_LONG).show()

inline fun <reified T : Activity> Context.intentFor() =
	Intent(this, T::class.java)

inline fun <reified T : Activity> Context.startActivity(vararg extras: Pair<String, Any>) =
	startActivity(
		intentFor<T>()
			.apply {
				extras.forEach { (key, value) ->
					when (value) {
						is Int -> putExtra(key, value)
						is String -> putExtra(key, value)
					}
				}
			}
	)