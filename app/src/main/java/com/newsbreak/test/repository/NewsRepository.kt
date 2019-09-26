package com.newsbreak.test.repository

import android.content.Context
import com.newsbreak.test.api.service.OpenApiService
import com.newsbreak.test.db.dao.NewsDao
import com.newsbreak.test.utils.Utils
import io.reactivex.Maybe
import timber.log.Timber
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val openApiService: OpenApiService,
    private val newsDao: NewsDao,
    private val context: Context
) {
    fun loadNews(hasAppStarted: Boolean): Maybe<List<OpenApiService.News>> {
        val current = System.currentTimeMillis()
        val lastRefresh = Utils.readLastRefershTime(context)

        if (current - lastRefresh < EXPIRE_TIME) {
            return if (hasAppStarted) Maybe.empty() else newsDao.getNews()
        }

        return Maybe.fromCallable {
            // Remove expired news
            newsDao.deleteAll().blockingAwait()

            // Fetch news from Internet
            val newsList = openApiService.loadNews().blockingGet().newsList

            // Store it in database
            newsDao.insert(newsList).blockingAwait()

            // Record timestamp
            Timber.d("Fetch news from database and record timestamp $current")
            Utils.writeTimeStamp(context, current)

            newsDao.getNews().blockingGet()
        }
    }

    companion object {
        private const val EXPIRE_TIME = 15 * 1000L
    }
}