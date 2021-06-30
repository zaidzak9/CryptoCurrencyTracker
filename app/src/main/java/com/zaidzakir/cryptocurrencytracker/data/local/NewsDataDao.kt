package com.zaidzakir.cryptocurrencytracker.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.zaidzakir.cryptocurrencytracker.data.remote.newsResponse.Article

/**
 *Created by Zaid Zakir
 */
@Dao
interface NewsDataDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(article: Article): Long

    @Query("SELECT * FROM articles")
    fun getAllArticles(): LiveData<List<Article>>

    @Delete
    suspend fun deleteArticle(article: Article)
}