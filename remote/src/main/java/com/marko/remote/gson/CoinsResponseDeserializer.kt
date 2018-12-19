package com.marko.remote.gson

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.marko.remote.entities.CoinRemote
import com.marko.remote.entities.CoinStatus
import com.marko.remote.entities.CoinsResponse
import java.lang.reflect.Type

object CoinsResponseDeserializer : JsonDeserializer<CoinsResponse> {

	override fun deserialize(
		json: JsonElement,
		typeOfT: Type,
		context: JsonDeserializationContext
	): CoinsResponse {
		val data = json.asJsonObject.getAsJsonArray("data")

		val coins = data.map {
			it.asJsonObject.run {
				CoinRemote(
					id = get("id").asInt,
					name = get("name").asString,
					symbol = get("symbol").asString,
					price = get("quote").asJsonObject["USD"].asJsonObject["price"].asFloat,
					priceInBTC = get("quote")?.asJsonObject?.get("BTC")?.asJsonObject?.get("price")?.asFloat ?: -1f,
					inExistenceSupply = get("total_supply").asLong,
					circulatingSupply = get("circulating_supply").asLong,
					maxSupply = get("max_supply")?.takeIf { isJsonNull }?.asLong ?: -1L,
					tradedIn24h = get("quote").asJsonObject["USD"].asJsonObject["volume_24h"].asLong
				)
			}
		}

		val statusJson = json.asJsonObject.getAsJsonObject("status")

		val status = CoinStatus(
			timestamp = statusJson["timestamp"].asString,
			errorCode = statusJson["error_code"].asInt,
			elapsed = statusJson["elapsed"].asInt,
			creditCount = statusJson["credit_count"].asInt
		)


		return CoinsResponse(coins = coins, status = status)
	}
}