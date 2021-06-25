package com.zaidzakir.cryptocurrencytracker.di

import android.content.Context
import androidx.room.Room
import com.zaidzakir.cryptocurrencytracker.data.local.CoinDatabase
import com.zaidzakir.cryptocurrencytracker.data.local.NewsDatabase
import com.zaidzakir.cryptocurrencytracker.data.remote.CryptoApi
import com.zaidzakir.cryptocurrencytracker.data.remote.NewsApi
import com.zaidzakir.cryptocurrencytracker.repositories.remote.MainRepositories
import com.zaidzakir.cryptocurrencytracker.repositories.remote.DefaultRepository
import com.zaidzakir.cryptocurrencytracker.util.Constants.BASE_URL_LUNARCRASH_API
import com.zaidzakir.cryptocurrencytracker.util.Constants.BASE_URL_NEWS_API
import com.zaidzakir.cryptocurrencytracker.util.Constants.DATABASE_NAME
import com.zaidzakir.cryptocurrencytracker.util.Constants.DATABASE_NEWS_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


/**
 *Created by Zaid Zakir
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideCryptoApi(): CryptoApi {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()

        return Retrofit.Builder()
                .baseUrl(BASE_URL_LUNARCRASH_API)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
                .create(CryptoApi::class.java)
    }

    @Singleton
    @Provides
    fun provideNewsApi(): NewsApi {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL_NEWS_API)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(NewsApi::class.java)
    }

    @Singleton
    @Provides
    fun provideDefaultRepository(
        lunarCrushApi: CryptoApi,
        newsApi: NewsApi,
        newsDatabase: NewsDatabase
    ) = DefaultRepository(lunarCrushApi, newsApi, newsDatabase) as MainRepositories

    @Singleton
    @Provides
    fun provideCoinDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context, CoinDatabase::class.java, DATABASE_NAME).build()

    @Singleton
    @Provides
    fun provideNewsDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context, NewsDatabase::class.java, DATABASE_NEWS_NAME).build()

}