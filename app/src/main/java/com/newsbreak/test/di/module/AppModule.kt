package com.newsbreak.test.di.module

import android.app.Application
import android.content.Context
import com.newsbreak.test.api.service.OpenApiService
import com.newsbreak.test.db.dao.NewsDao
import com.newsbreak.test.repository.NewsRepository
import com.newsbreak.test.ui.adapter.NewsAdapter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {
    @Singleton
    @Provides
    fun provideNewsRepository(openApiService: OpenApiService, newsDao: NewsDao, context: Context)
            = NewsRepository(openApiService, newsDao, context)

    @Singleton
    @Provides
    fun provideNewsAdapter() = NewsAdapter()

    @Singleton
    @Provides
    fun provideApplicaton(application: Application) = application.applicationContext
}