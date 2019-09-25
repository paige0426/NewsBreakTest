package com.newsbreak.test.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.newsbreak.test.api.service.OpenApiService
import io.reactivex.Completable
import io.reactivex.Maybe

@Dao
interface NewsDao {
    @Query("DELETE FROM news_table")
    fun deleteAll(): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(newsList: List<OpenApiService.News>): Completable

    @Query("SELECT * FROM news_table ORDER BY date DESC")
    fun getNews(): Maybe<List<OpenApiService.News>>
}