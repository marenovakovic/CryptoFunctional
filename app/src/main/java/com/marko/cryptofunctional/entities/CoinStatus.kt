package com.marko.cryptofunctional.entities

import com.google.gson.annotations.SerializedName

data class CoinStatus(
	val timestamp: String = "",
	@SerializedName("error_code") val errorCode: Int = -1,
	val elapsed: Int = -1,
	@SerializedName("credit_count") val creditCount: Int = -1
)
