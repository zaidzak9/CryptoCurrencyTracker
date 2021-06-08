package com.zaidzakir.cryptocurrencytracker.repositories.remote

import com.zaidzakir.cryptocurrencytracker.data.remote.LunarCrushApi
import com.zaidzakir.cryptocurrencytracker.data.remote.response.CrypoMarketMainResponse
import com.zaidzakir.cryptocurrencytracker.util.Resource
import java.lang.Exception
import javax.inject.Inject

/**
 *Created by Zaid Zakir
 */
class DefaultRepository @Inject constructor(
    private val lunarCrushApi: LunarCrushApi
):CryptoRepositories {

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
}