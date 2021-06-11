package com.zaidzakir.cryptocurrencytracker.data.remote

import com.zaidzakir.cryptocurrencytracker.BuildConfig
import com.zaidzakir.cryptocurrencytracker.data.remote.response.CrypoMarketMainResponse
import com.zaidzakir.cryptocurrencytracker.util.Resource
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 *Created by Zaid Zakir
 */
interface LunarCrushApi {

    @GET("v2?data=market")
    suspend fun getCoinsMarket(
            @Query("key") apiKey:String? = BuildConfig.API_KEY,
            @Query("type") type:String? = "fast",
            @Query("limit") limit :Int? = null,
            @Query("page") page :Int? = null,
    ):Response<CrypoMarketMainResponse>
}