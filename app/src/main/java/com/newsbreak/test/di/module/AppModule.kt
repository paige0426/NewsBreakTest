package com.newsbreak.test.di.module

import com.newsbreak.test.api.service.OpenApiService
import com.newsbreak.test.repository.NewsRepository
import com.newsbreak.test.ui.adapter.NewsAdapter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {
    @Singleton
    @Provides
    fun provideNewsRepository(openApiService: OpenApiService): NewsRepository = NewsRepository(openApiService)

    @Singleton
    @Provides
    fun provideNewsAdapter(): NewsAdapter = NewsAdapter()
}