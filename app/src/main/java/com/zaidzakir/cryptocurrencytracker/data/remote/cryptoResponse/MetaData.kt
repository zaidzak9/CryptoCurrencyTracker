package com.zaidzakir.cryptocurrencytracker.data.remote.cryptoResponse

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "coin_meta_data")
data class MetaData(
        val description: String?,
        val header_image: String?,
        val header_text: String?,
        @PrimaryKey(autoGenerate = true)
        val id: Int? = null,
        val image: String?,
        val name: String?,
        val short_summary: String?,
        val symbol: String?,
        val twitter_accounts: String?,
        val twitter_link: String?,
        val website_link: String?,
) : Serializable