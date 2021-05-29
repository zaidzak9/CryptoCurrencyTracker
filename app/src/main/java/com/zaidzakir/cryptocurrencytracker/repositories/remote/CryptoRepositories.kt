package com.zaidzakir.cryptocurrencytracker.repositories.remote

import com.zaidzakir.cryptocurrencytracker.data.remote.response.CrypoMarketMainResponse
import com.zaidzakir.cryptocurrencytracker.util.Resource
import kotlinx.coroutines.flow.StateFlow

/**
 *Created by Zaid Zakir
 */
interface CryptoRepositories {
    suspend fun getCoinsMarket():Resource<CrypoMarketMainResponse>
}