package com.zaidzakir.cryptocurrencytracker.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.zaidzakir.cryptocurrencytracker.data.remote.cryptoResponse.CoinData
import com.zaidzakir.cryptocurrencytracker.data.remote.cryptoResponse.MetaData

/**
 *Created by Zaid Zakir
 */
@Database(entities = [CoinData::class, MetaData::class], version = 2)
abstract class CoinDatabase : RoomDatabase() {
    abstract fun getCoinDataDao() : CoinDataDao
}