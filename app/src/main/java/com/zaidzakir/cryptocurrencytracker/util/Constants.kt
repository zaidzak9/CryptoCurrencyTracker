package com.zaidzakir.cryptocurrencytracker.util

import com.zaidzakir.cryptocurrencytracker.data.remote.cryptoResponse.MetaData
import java.util.*

/**
 *Created by Zaid Zakir
 */
object Constants {

    const val BASE_URL_LUNARCRASH_API = "https://api.lunarcrush.com/"
    const val BASE_URL_NEWS_API = "https://newsapi.org/"
    const val DATABASE_NAME = "crypto_database"
    const val DATABASE_NEWS_NAME = "news_database"
    const val NUM_TABS = 2
    const val COIN_LIMIT = 100
    const val INTERVAL = "day"
    const val TYPE = "fast"
    const val CHANGE = "1m"
    const val DATA_POINTS = 10
    const val TIME_SERIES_VALUES = "time,open,close"
    var cryptoMetaData = listOf<MetaData>()
    var cryptoHashData = hashMapOf<Int, String>()
}