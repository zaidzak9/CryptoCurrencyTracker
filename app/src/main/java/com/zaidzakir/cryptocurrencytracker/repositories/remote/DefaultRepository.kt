package com.zaidzakir.cryptocurrencytracker.repositories.remote

import androidx.room.withTransaction
import com.zaidzakir.cryptocurrencytracker.data.local.CoinDatabase
import com.zaidzakir.cryptocurrencytracker.data.remote.LunarCrushApi
import com.zaidzakir.cryptocurrencytracker.data.remote.response.CoinData
import com.zaidzakir.cryptocurrencytracker.data.remote.response.CrypoMarketMainResponse
import com.zaidzakir.cryptocurrencytracker.util.NetworkBoundResource
import com.zaidzakir.cryptocurrencytracker.util.Resource
import kotlinx.coroutines.delay
import java.lang.Exception
import javax.inject.Inject

/**
 *Created by Zaid Zakir
 */
class DefaultRepository @Inject constructor(
    private val lunarCrushApi: LunarCrushApi,
    private val coinDatabase: CoinDatabase
):CryptoRepositories {

    private val coinDataDao = coinDatabase.getCoinDataDao()

    fun getCoinDataDao()= NetworkBoundResource(
        query = {
            coinDataDao.getCoinData()
        },
        fetch = {
            delay(1000)
            lunarCrushApi.getCoinsMarket()
        },
        saveFetchResult = { coinData ->
            coinDatabase.withTransaction {
                coinDataDao.deleteCoinData()
                coinDataDao.insertCoinData(coinData.body()!!.data)
            }
        }
    )

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