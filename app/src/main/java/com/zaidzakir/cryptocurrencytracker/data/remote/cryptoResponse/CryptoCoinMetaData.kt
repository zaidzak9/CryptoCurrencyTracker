package com.zaidzakir.cryptocurrencytracker.data.remote.cryptoResponse

data class CryptoCoinMetaData(
        val config: ConfigX,
        val `data`: List<MetaData>,
        val usage: UsageX
)