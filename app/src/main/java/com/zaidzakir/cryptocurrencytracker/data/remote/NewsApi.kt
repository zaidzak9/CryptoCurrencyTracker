package com.zaidzakir.cryptocurrencytracker.data.remote

import com.zaidzakir.cryptocurrencytracker.BuildConfig
import com.zaidzakir.cryptocurrencytracker.data.remote.newsResponse.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 *Created by Zaid Zakir
 */
interface NewsApi {
    @GET("v2/everything")
    suspend fun searchForNews(
        @Query("q")
        searchQuery: String,
        @Query("apiKey")
        apiKey: String = BuildConfig.NEWS_API_KEY
    ) : Response<NewsResponse>
}