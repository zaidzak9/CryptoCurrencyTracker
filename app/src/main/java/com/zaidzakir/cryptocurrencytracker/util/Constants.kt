package com.zaidzakir.cryptocurrencytracker.util

import com.zaidzakir.cryptocurrencytracker.data.remote.cryptoResponse.MetaData

/**
 *Created by Zaid Zakir
 */
object Constants {

    const val BASE_URL_LUNARCRASH_API = "https://api.lunarcrush.com/"
    const val BASE_URL_NEWS_API = "https://newsapi.org/"
    const val DATABASE_NAME = "crypto_database"
    const val DATABASE_NEWS_NAME = "news_database"
    const val NUM_TABS = 2
    lateinit var cryptoMetaData: List<MetaData>
}