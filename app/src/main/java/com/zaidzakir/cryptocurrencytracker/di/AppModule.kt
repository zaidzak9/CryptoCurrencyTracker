package com.zaidzakir.cryptocurrencytracker.di

import com.zaidzakir.cryptocurrencytracker.repositories.remote.LunarCrushApi
import com.zaidzakir.cryptocurrencytracker.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
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
    fun provideCryptoApi():LunarCrushApi{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(LunarCrushApi::class.java)
    }
}