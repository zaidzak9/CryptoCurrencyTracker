package com.zaidzakir.cryptocurrencytracker.data.local

import androidx.room.Database
import androidx.room.Entity
import androidx.room.RoomDatabase
import com.zaidzakir.cryptocurrencytracker.data.remote.response.CoinData

/**
 *Created by Zaid Zakir
 */
@Database(entities = [CoinData::class],version = 1)
abstract class CoinDatabase : RoomDatabase() {

    abstract fun getCoinDataDao() : CoinDataDao
}