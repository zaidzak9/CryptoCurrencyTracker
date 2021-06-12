package com.zaidzakir.cryptocurrencytracker.repositories.remote

import com.zaidzakir.cryptocurrencytracker.data.remote.cryptoResponse.CrypoMarketMainResponse
import com.zaidzakir.cryptocurrencytracker.data.remote.newsResponse.NewsResponse
import com.zaidzakir.cryptocurrencytracker.util.Resource

/**
 *Created by Zaid Zakir
 */
interface MainRepositories {
    suspend fun getCoinsMarket():Resource<CrypoMarketMainResponse>
    suspend fun getNewsApi():Resource<NewsResponse>
}