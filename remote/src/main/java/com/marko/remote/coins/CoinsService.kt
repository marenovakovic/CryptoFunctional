package com.marko.remote.coins

import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.marko.domain.entities.CoinId
import com.marko.remote.entities.CoinResponse
import com.marko.remote.entities.CoinsResponse
import com.marko.remote.gson.CoinResponseDeserializer
import com.marko.remote.gson.CoinsResponseDeserializer
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.*
import java.util.concurrent.TimeUnit

private const val API_KEY = "api_key"

/**
 * [Retrofit] service defining how to fetch [CoinsResponse]
 */
interface CoinsService {

	/**
	 * Fetch [Coin] [List] from server
	 *
	 * @param apiKey API key for authorization
	 *
	 * @return [Deferred] with the server response
	 */
	@GET("cryptocurrency/listings/latest")
	fun getCoins(@Query("CMC_PRO_API_KEY") apiKey: String = API_KEY): Deferred<CoinsResponse>

	/**
	 * Fetch [Coin] details from server
	 *
	 * @param coinId [CoinId] of [Coin] whose details are requested
	 *
	 * @param map [HashMap] containing query parameters
	 *
	 * @return [Deferred] containing response from server
	 */
	@GET("cryptocurrency/info")
	fun getCoinDetails(
		@Query("CMC_PRO_API_KEY") apiKey: String = API_KEY,
		@Query("id") coinId: CoinId
	): Deferred<CoinResponse>
}

/**
 * Http read request timeout
 */
private const val READ_TIMEOUT = 15L

/**
 * Http write request timeout
 */
private const val WRITE_TIMEOUT = 15L

/**
 * [OkHttpClient] that defines read [READ_TIMEOUT] and write [WRITE_TIMEOUT] timeouts
 */
private val okHttpClient = OkHttpClient.Builder()
	.readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
	.writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
	.build()

/**
 * [Gson] instance that has [CoinsResponseDeserializer] registered for deserializing [CoinsResponse] from API
 */
private val gson = GsonBuilder()
	.registerTypeAdapter(CoinsResponse::class.java, CoinsResponseDeserializer)
	.registerTypeAdapter(CoinResponse::class.java, CoinResponseDeserializer)
	.create()

/**
 * [Retrofit] instance
 */
private val retrofit = Retrofit.Builder()
	.client(okHttpClient)
	.baseUrl("https://pro-api.coinmarketcap.com/v1/")
	.addConverterFactory(GsonConverterFactory.create(gson))
	.addCallAdapterFactory(CoroutineCallAdapterFactory())
	.build()

/**
 * [CoinsService] build with [retrofit]
 */
val coinsService: CoinsService = retrofit.create(CoinsService::class.java)