package com.zaidzakir.cryptocurrencytracker.data.remote.cryptoResponse

data class CrypoMarketMainResponse(
    val config: Config,
    val data: List<CoinData>,
    val usage: Usage
)