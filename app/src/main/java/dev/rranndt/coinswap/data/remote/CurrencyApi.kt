package dev.rranndt.coinswap.data.remote

import dev.rranndt.coinswap.data.remote.dto.CurrencyDto
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyApi {
    @GET("v1/latest")
    suspend fun getLatestRates(
        @Query("apikey") apiKey: String = API_KEY
    ): CurrencyDto

    companion object {
        const val API_KEY = "fca_live_jAiOU7wT60GwHhnZV0rlUb3bWhKnLNGuxVVt02ym"
        const val BASE_URL = "https://api.freecurrencyapi.com/"
    }
}