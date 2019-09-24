package com.newsbreak.test.repository

import com.newsbreak.test.api.service.OpenApiService
import javax.inject.Inject

class NewsRepository @Inject constructor(private val openApiService: OpenApiService) {
    fun loadNews() = openApiService.loadNews()
}