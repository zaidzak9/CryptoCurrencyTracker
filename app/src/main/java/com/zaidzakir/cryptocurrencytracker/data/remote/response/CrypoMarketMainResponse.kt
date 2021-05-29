package com.zaidzakir.cryptocurrencytracker.data.remote.response

data class CrypoMarketMainResponse(
    val config: Config,
    val data: List<CoinData>,
    val usage: Usage
)