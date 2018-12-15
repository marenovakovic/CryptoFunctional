package com.marko.cryptofunctional.entities

import com.google.gson.annotations.SerializedName

data class CoinsResponse(
	@SerializedName("data") val coins: List<Coin>,
	val metadata: CoinsMetadata
)