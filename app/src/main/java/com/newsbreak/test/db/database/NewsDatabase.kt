package com.newsbreak.test.db.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.newsbreak.test.api.service.OpenApiService
import com.newsbreak.test.db.dao.NewsDao

@Database(entities = [OpenApiService.News::class], version = 1)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun newsDao(): NewsDao
}