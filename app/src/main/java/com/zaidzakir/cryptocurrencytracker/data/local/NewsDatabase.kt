package com.zaidzakir.cryptocurrencytracker.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.zaidzakir.cryptocurrencytracker.data.local.converters.Converters
import com.zaidzakir.cryptocurrencytracker.data.remote.cryptoResponse.CoinData
import com.zaidzakir.cryptocurrencytracker.data.remote.newsResponse.Article

/**
 *Created by Zaid Zakir
 */
@Database(entities = [Article::class], version = 1)
@TypeConverters(Converters::class)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun getNewsDataDao(): NewsDataDao
}