package com.newsbreak.test.di.module

import android.content.Context
import androidx.room.Room
import com.newsbreak.test.db.database.NewsDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {
    @Singleton
    @Provides
    fun provideNewsDatabase(context: Context) = Room
        .databaseBuilder(
            context.applicationContext,
            NewsDatabase::class.java,
            "News_database")
        .build()

    @Singleton
    @Provides
    fun provideNewsDao(newsDatabase: NewsDatabase) = newsDatabase.newsDao()
}