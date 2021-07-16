package com.zaidzakir.cryptocurrencytracker.data.remote.cryptoResponse

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "coin_data")
data class CoinData(
        @PrimaryKey(autoGenerate = true)
        val id: Int?,
        val acr: Double?, //altRank
        val mc: Double?,//marketCap
        val n: String?,//name
        val p: Double?,//price
        val p_btc: Double?,//price(btc)
        val pc: Double?,//percentage change 24h
        val pch: Double?,//percentage change 1h
        val s: String?,//symbol
) : Serializable