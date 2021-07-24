package com.zaidzakir.cryptocurrencytracker.data.remote.cryptoTimeSeriesResponse

data class Config(
        val change: String,
        val `data`: String,
        val data_points: Int,
        val interval: String,
        val symbol: String,
        val time_series_indicators: String
)