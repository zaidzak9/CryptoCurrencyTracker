package com.zaidzakir.cryptocurrencytracker.repositories.remote

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.zaidzakir.cryptocurrencytracker.BuildConfig
import com.zaidzakir.cryptocurrencytracker.data.CryptoPagingSource
import com.zaidzakir.cryptocurrencytracker.data.local.CoinDatabase
import com.zaidzakir.cryptocurrencytracker.data.local.NewsDatabase
import com.zaidzakir.cryptocurrencytracker.data.remote.CryptoApi
import com.zaidzakir.cryptocurrencytracker.data.remote.NewsApi
import com.zaidzakir.cryptocurrencytracker.data.remote.cryptoResponse.CryptoCoinMetaData
import com.zaidzakir.cryptocurrencytracker.data.remote.cryptoResponse.CryptoMarketMainResponse
import com.zaidzakir.cryptocurrencytracker.data.remote.cryptoResponse.MetaData
import com.zaidzakir.cryptocurrencytracker.data.remote.newsResponse.Article
import com.zaidzakir.cryptocurrencytracker.data.remote.newsResponse.NewsResponse
import com.zaidzakir.cryptocurrencytracker.util.Resource
import java.lang.Exception
import javax.inject.Inject

/**
 *Created by Zaid Zakir
 */
class DefaultRepository @Inject constructor(
    private val lunarCrushApi: CryptoApi,
    private val newsApi: NewsApi,
    private val newsDatabase: NewsDatabase,
    private val coinDatabase: CoinDatabase,
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

    override suspend fun getCoinsMarket(): Resource<CryptoMarketMainResponse> {
        return try {
            val response = lunarCrushApi.getCoinsMarket()
            if (response.isSuccessful) {
                response.body()?.let { cryptoResponse ->
                    return@let Resource.Success(cryptoResponse)
                } ?: Resource.Error("An unknown error occurred")
            } else {
                Resource.Error("An unknown error occurred")
            }
        } catch (e: Exception) {
            return Resource.Error("Something went wrong! $e")
        }
    }

    override suspend fun getNewsApi(): Resource<NewsResponse> {
        return try {
            val response = newsApi.searchForNews("crypto currency",BuildConfig.NEWS_API_KEY)

            if (response.isSuccessful){
                response.body()?.let {
                    return@let Resource.Success(it)
                } ?: Resource.Error("An unknown error occurred")
            } else {
                Resource.Error("An unknown error occurred")
            }
        } catch (e: Exception) {
            return Resource.Error("Something went wrong! $e")
        }
    }

    override suspend fun saveNews(article: Article) {
        newsDatabase.getNewsDataDao().upsert(article)
    }

    override fun getSavedNews(): LiveData<List<Article>> {
        return newsDatabase.getNewsDataDao().getAllArticles()
    }

    override suspend fun deleteArticle(article: Article) {
        newsDatabase.getNewsDataDao().deleteArticle(article)
    }

    override suspend fun getCoinMetaData(): Resource<CryptoCoinMetaData> {
        return try {
            val response = lunarCrushApi.getCoinsMetaData()

            if (response.isSuccessful) {
                response.body()?.let { coinMetaData ->
                    return@let Resource.Success(coinMetaData)
                } ?: Resource.Error("An unknown error occurred")
            } else {
                Resource.Error("An unknown error occurred")
            }
        } catch (e: Exception) {
            return Resource.Error("Something went wrong! $e")
        }
    }

    override fun getSavedCoinMetaData(): LiveData<List<MetaData>> {
        return coinDatabase.getCoinDataDao().getCoinMetaData()
    }

    override suspend fun saveCoinMetaData(cryptoCoinMetaData: List<MetaData>) {
        coinDatabase.getCoinDataDao().insertCoinMetaData(cryptoCoinMetaData)
    }


}