package com.zaidzakir.cryptocurrencytracker.repositories.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.zaidzakir.cryptocurrencytracker.BuildConfig
import com.zaidzakir.cryptocurrencytracker.data.CryptoPagingSource
import com.zaidzakir.cryptocurrencytracker.data.remote.CryptoApi
import com.zaidzakir.cryptocurrencytracker.data.remote.NewsApi
import com.zaidzakir.cryptocurrencytracker.data.remote.cryptoResponse.CrypoMarketMainResponse
import com.zaidzakir.cryptocurrencytracker.data.remote.newsResponse.NewsResponse
import com.zaidzakir.cryptocurrencytracker.util.Resource
import java.lang.Exception
import javax.inject.Inject

/**
 *Created by Zaid Zakir
 */
class DefaultRepository @Inject constructor(
    private val lunarCrushApi: CryptoApi,
    private val newsApi: NewsApi
):MainRepositories {

    fun getCoinsMarketPaging()=
        Pager(
            config = PagingConfig(
                pageSize = 15,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {CryptoPagingSource(lunarCrushApi)}
        ).liveData

    override suspend fun getCoinsMarket(): Resource<CrypoMarketMainResponse> {
        return try {
            val response = lunarCrushApi.getCoinsMarket()
            if (response.isSuccessful){
                response.body()?.let {cryptoResponse ->
                    return@let Resource.Success(cryptoResponse)
                }?: Resource.Error("An unknown error occurred")
            }else{
                Resource.Error("An unknown error occurred")
            }
        }catch (e: Exception){
            return Resource.Error("Something went wrong! $e")
        }
    }

    override suspend fun getNewsApi(): Resource<NewsResponse> {
        return try {
            val response = newsApi.searchForNews("crypto currency",BuildConfig.NEWS_API_KEY)

            if (response.isSuccessful){
                response.body()?.let {
                    return@let Resource.Success(it)
                }?:Resource.Error("An unknown error occurred")
            }else{
                Resource.Error("An unknown error occurred")
            }
        }catch (e: Exception){
            return Resource.Error("Something went wrong! $e")
        }
    }
}