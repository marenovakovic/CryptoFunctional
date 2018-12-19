package com.marko.remote.entities

data class CoinsResponse(
	val coins: List<CoinRemote>,
	val status: CoinStatus
)