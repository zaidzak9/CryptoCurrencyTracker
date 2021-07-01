package com.zaidzakir.cryptocurrencytracker.repositories.remote

import androidx.lifecycle.LiveData
import com.zaidzakir.cryptocurrencytracker.data.remote.cryptoResponse.CryptoCoinMetaData
import com.zaidzakir.cryptocurrencytracker.data.remote.cryptoResponse.CryptoMarketMainResponse
import com.zaidzakir.cryptocurrencytracker.data.remote.newsResponse.Article
import com.zaidzakir.cryptocurrencytracker.data.remote.newsResponse.NewsResponse
import com.zaidzakir.cryptocurrencytracker.util.Resource

/**
 *Created by Zaid Zakir
 */
interface MainRepositories {
    suspend fun getCoinsMarket(): Resource<CryptoMarketMainResponse>
    suspend fun getNewsApi(): Resource<NewsResponse>
    suspend fun saveNews(article: Article)
    fun getSavedNews(): LiveData<List<Article>>
    suspend fun deleteArticle(article: Article)
    suspend fun getCoinMetaData(): Resource<CryptoCoinMetaData>
}