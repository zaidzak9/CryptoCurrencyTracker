package com.zaidzakir.cryptocurrencytracker.di

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import com.zaidzakir.cryptocurrencytracker.data.local.CoinDatabase
import com.zaidzakir.cryptocurrencytracker.data.remote.LunarCrushApi
import com.zaidzakir.cryptocurrencytracker.repositories.remote.CryptoRepositories
import com.zaidzakir.cryptocurrencytracker.repositories.remote.DefaultRepository
import com.zaidzakir.cryptocurrencytracker.util.Constants
import com.zaidzakir.cryptocurrencytracker.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
            lunarCrushApi: LunarCrushApi
    ) = DefaultRepository(lunarCrushApi) as CryptoRepositories

    @Singleton
    @Provides
    fun provideRoomDatabase(application: Application): CoinDatabase {
       return Room.databaseBuilder(
                application,
                CoinDatabase::class.java,
                Constants.DATABASE_NAME).build()
    }
}