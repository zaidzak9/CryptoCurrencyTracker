package com.zaidzakir.cryptocurrencytracker.data.remote.cryptoTimeSeriesResponse

data class CryptoTimeSeriesMain(
        val config: Config,
        val `data`: List<Data>,
        val usage: Usage
)