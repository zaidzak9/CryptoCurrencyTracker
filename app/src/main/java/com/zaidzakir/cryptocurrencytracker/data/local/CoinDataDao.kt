package com.zaidzakir.cryptocurrencytracker.data.local

import androidx.room.*
import com.zaidzakir.cryptocurrencytracker.data.remote.cryptoResponse.CoinData
import kotlinx.coroutines.flow.Flow

/**
 *Created by Zaid Zakir
 */
@Dao
interface CoinDataDao {
    @Query("SELECT * FROM coin_data")
    fun getCoinData():Flow<List<CoinData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCoinData(coinData: List<CoinData>)

    @Query("DELETE FROM coin_data")
    suspend fun deleteCoinData()
}