package com.marko.remote.gson

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.marko.remote.entities.CoinRemote
import com.marko.remote.entities.CoinResponse
import com.marko.remote.entities.CoinStatus
import java.lang.reflect.Type

object CoinResponseDeserializer : JsonDeserializer<CoinResponse> {

	override fun deserialize(
		json: JsonElement,
		typeOfT: Type,
		context: JsonDeserializationContext
	): CoinResponse {
		val data = json.asJsonObject.getAsJsonObject("data")

		val coin = data.entrySet().first().value.run {
			CoinRemote(
				id = 1,
				name = "",
				symbol = "",
				logo = asJsonObject?.get("logo")?.asString ?: "",
				price = -1f,
				priceInBTC = -1f,
				inExistenceSupply = 1L,
				circulatingSupply = 1L,
				maxSupply = 1L,
				tradedIn24h = 1L
			)
		}

		val statusJson = json.asJsonObject.getAsJsonObject("status")

		val status = CoinStatus(
			timestamp = statusJson["timestamp"].asString,
			errorCode = statusJson["error_code"].asInt,
			elapsed = statusJson["elapsed"].asInt,
			creditCount = statusJson["credit_count"].asInt
		)

		return CoinResponse(coin = coin, status = status)
	}
}