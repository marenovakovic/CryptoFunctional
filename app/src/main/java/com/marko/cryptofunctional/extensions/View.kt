package com.marko.cryptofunctional.extensions

import android.view.View

fun View.beVisible() {
	visibility = View.VISIBLE
}

fun View.beInvisible() {
	visibility = View.INVISIBLE
}

fun View.beGone() {
	visibility = View.GONE
}

fun View.beVisibleIf(condition: Boolean) {
	visibility = if (condition) View.VISIBLE else View.GONE
}