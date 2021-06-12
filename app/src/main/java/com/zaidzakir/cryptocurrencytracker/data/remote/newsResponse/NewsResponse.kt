package com.zaidzakir.cryptocurrencytracker.data.remote.newsResponse


data class NewsResponse(
    val articles: MutableList<Article>,
    val status: String,
    val totalResults: Int
)