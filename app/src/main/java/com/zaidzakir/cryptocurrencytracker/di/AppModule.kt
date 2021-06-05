package com.zaidzakir.cryptocurrencytracker.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.zaidzakir.cryptocurrencytracker.data.local.CoinDatabase
import com.zaidzakir.cryptocurrencytracker.data.remote.LunarCrushApi
import com.zaidzakir.cryptocurrencytracker.repositories.remote.CryptoRepositories
import com.zaidzakir.cryptocurrencytracker.repositories.remote.DefaultRepository
import com.zaidzakir.cryptocurrencytracker.util.Constants
import com.zaidzakir.cryptocurrencytracker.util.Constants.BASE_URL
import com.zaidzakir.cryptocurrencytracker.util.Constants.DATABASE_NAME
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
    fun provideCryptoApi(): LunarCrushApi {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()

        return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
                .create(LunarCrushApi::class.java)
    }

    @Singleton
    @Provides
    fun provideDefaultRepository(
            lunarCrushApi: LunarCrushApi,
            coinDatabase: CoinDatabase
    ) = DefaultRepository(lunarCrushApi,coinDatabase) as CryptoRepositories

    @Singleton
    @Provides
    fun provideShoppingItemDatabase (@ApplicationContext context: Context
    ) = Room.databaseBuilder(context, CoinDatabase::class.java, DATABASE_NAME).build()

}