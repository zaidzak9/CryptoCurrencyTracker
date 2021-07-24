package com.zaidzakir.cryptocurrencytracker.data.remote

import com.zaidzakir.cryptocurrencytracker.BuildConfig
import com.zaidzakir.cryptocurrencytracker.data.remote.cryptoResponse.CryptoMarketMainResponse
import com.zaidzakir.cryptocurrencytracker.data.remote.cryptoResponse.CryptoCoinMetaData
import com.zaidzakir.cryptocurrencytracker.data.remote.cryptoTimeSeriesResponse.CryptoTimeSeriesMain
import com.zaidzakir.cryptocurrencytracker.util.Constants.CHANGE
import com.zaidzakir.cryptocurrencytracker.util.Constants.COIN_LIMIT
import com.zaidzakir.cryptocurrencytracker.util.Constants.DATA_POINTS
import com.zaidzakir.cryptocurrencytracker.util.Constants.INTERVAL
import com.zaidzakir.cryptocurrencytracker.util.Constants.TIME_SERIES_VALUES
import com.zaidzakir.cryptocurrencytracker.util.Constants.TYPE
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 *Created by Zaid Zakir
 */
interface CryptoApi {

    @GET("v2?data=market")
    suspend fun getCoinsMarket(
            @Query("key") apiKey: String = BuildConfig.API_KEY,
            @Query("sort") sort: String,
            @Query("desc") order: Boolean,
            @Query("limit") limit: Int? = COIN_LIMIT,
            @Query("type") type: String = TYPE,
    ): Response<CryptoMarketMainResponse>

    /**
     *   Details, overall metrics, and time series metrics for coins to build chart
     *   @Query("symbol") - symbols to fetch data for.
     *   @Query("interval") - Provide an interval string value of either "hour" or "day". Defaults to "hour" if ommited
     *   @Query("time_series_indicators") - A comma-separated list of metrics to include in the time series values.
     *   All available metrics provided if parameter is ommited.
     *   @Query("change") - A comma-separated list of change intervals to provide metrics for. 1d,1w,1m,3m,6m,1y.
     *   Output will include the sum of the selected interval (such as 3 months) the previous sum of the time period before and the percent change
     *   @Query("data_points") - Number of time series data points to include for the asset. Default = 24
     *   Maximum of 720 data points accepted, to not use time series data set data_points=0
     *   more info at - https://lunarcrush.com/developers/docs under ASSETS
     */
    @GET("v2?data=assets")
    suspend fun getCoinTimeSeries(
            @Query("key") apiKey: String = BuildConfig.API_KEY,
            @Query("symbol") symbol: String,
            @Query("interval") interval: String = INTERVAL,
            @Query("time_series_indicators") timeSeriesIndicators: String = TIME_SERIES_VALUES,
            @Query("change") change: String = CHANGE,
            @Query("data_points") dataPoints: Int = DATA_POINTS,
    ): Response<CryptoTimeSeriesMain>

    @GET("v2?data=meta")
    suspend fun getCoinsMetaData(
            @Query("key") apiKey: String? = BuildConfig.API_KEY,
            @Query("type") type: String? = "full"
    ): Response<CryptoCoinMetaData>
}