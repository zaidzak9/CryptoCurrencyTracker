package com.zaidzakir.cryptocurrencytracker.data.remote.response

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "coindata")
data class CoinData(
        val acr: Double,
        val `as`: Double,
        val bl: Double,
        val br: Double,
        val bsc: Double,
        val c: Double,
        val cr: Double,
        val d: Double,
        val df: Double,
        val dot: Double,
        val e2: Double,
        val gs: Double,
        val mc: Double,
        val md: Double,
        @PrimaryKey val n: String,
        val na: Double,
        val nft: Double,
        val p: Double,
        val p_btc: Double,
        val pc: Double,
        val pch: Double,
        val r: Double,
        val s: String,
        val sc: Double,
        val sd: Double,
        val sp: Double,
        val ss: Double,
        val sv: Double,
        val t: Double,
        val u: Double,
        val v: Any,
        val vt: Double,
        val yt: Double
)