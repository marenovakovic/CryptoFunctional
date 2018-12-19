package com.marko.remote.entities

data class CoinsMetadata(
	val timestamp: Long = - 1,
	val numberOfCoins: Int? = null,
	val error: Any? = null
)