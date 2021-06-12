package com.zaidzakir.cryptocurrencytracker.data.remote

import com.zaidzakir.cryptocurrencytracker.BuildConfig
import com.zaidzakir.cryptocurrencytracker.data.remote.cryptoResponse.CrypoMarketMainResponse
import com.zaidzakir.cryptocurrencytracker.data.remote.newsResponse.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 *Created by Zaid Zakir
 */
interface CryptoApi {

    @GET("v2?data=market")
    suspend fun getCoinsMarket(
            @Query("key") apiKey:String? = BuildConfig.API_KEY,
            @Query("type") type:String? = "fast",
            @Query("limit") limit :Int? = null,
            @Query("page") page :Int? = null,
    ):Response<CrypoMarketMainResponse>
}