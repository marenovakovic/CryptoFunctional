package com.marko.cryptofunctional.entities

import com.google.gson.annotations.SerializedName

data class CoinsMetadata(
	val timestamp: Long = -1,
	@SerializedName("num_cryptocurrencies") val numberOfCoins: Int? = null,
	val error: Any? = null
)