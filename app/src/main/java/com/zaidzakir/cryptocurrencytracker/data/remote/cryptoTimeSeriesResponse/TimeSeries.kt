package com.zaidzakir.cryptocurrencytracker.data.remote.cryptoTimeSeriesResponse

data class TimeSeries(
        val close: Double,
        val `open`: Double,
        val time: Int
)