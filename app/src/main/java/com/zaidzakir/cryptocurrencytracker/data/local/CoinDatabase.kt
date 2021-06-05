package com.zaidzakir.cryptocurrencytracker.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.zaidzakir.cryptocurrencytracker.data.remote.response.CoinData
import com.zaidzakir.cryptocurrencytracker.util.Converters

/**
 *Created by Zaid Zakir
 */
@Database(entities = [CoinData::class],version = 1)
abstract class CoinDatabase : RoomDatabase() {

    abstract fun getCoinDataDao() : CoinDataDao
}