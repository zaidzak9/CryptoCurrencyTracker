package com.zaidzakir.cryptocurrencytracker.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.zaidzakir.cryptocurrencytracker.data.remote.cryptoResponse.CoinData
import com.zaidzakir.cryptocurrencytracker.data.remote.cryptoResponse.CryptoCoinMetaData
import com.zaidzakir.cryptocurrencytracker.data.remote.cryptoResponse.MetaData
import kotlinx.coroutines.flow.Flow

/**
 *Created by Zaid Zakir
 */
@Dao
interface CoinDataDao {
    @Query("SELECT * FROM coin_data")
    fun getCoinData(): Flow<List<CoinData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCoinData(coinData: List<CoinData>)

    @Query("DELETE FROM coin_data")
    suspend fun deleteCoinData()

    @Query("SELECT * FROM coin_meta_data")
    fun getCoinMetaData(): LiveData<List<MetaData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCoinMetaData(metaData: List<MetaData>)
}