package com.zaidzakir.cryptocurrencytracker.data.remote.newsResponse

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.zaidzakir.cryptocurrencytracker.data.remote.newsResponse.Source
import java.io.Serializable
//
//@Entity(
//    tableName = "articles"
//)
data class Article(
//    @PrimaryKey(autoGenerate = true)
//    val id:Int? = null,
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    //need to create a type converter class to tell room ho to convert this, since it knows only to convert primitive types by default
    val source: Source?,
    val title: String?,
    val url: String?,
    val urlToImage: String?
):Serializable