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

        return Maybe.create { emitter ->
            newsDao.deleteAll().subscribe {
                Timber.d("Clear database complete")
                openApiService.loadNews().subscribe({
                    Timber.d("Receive news from Internet")
                    newsDao.insert(it.newsList).subscribe {
                        Timber.d("Insert news into database complete")
                        newsDao.getNews().subscribe({ news ->
                            Timber.d("Fetch news from database and record timestamp $current")
                            emitter.onSuccess(news)
                            Utils.writeTimeStamp(context, current)
                        }, { error ->
                            Timber.e("Error when fetch news from database")
                            emitter.onError(error)
                        }, {
                            Timber.d("Fetch news from database complete")
                            emitter.onComplete()
                        })
                    }
                }, {
                    Timber.e("Error when fetch news from Internet")
                    emitter.onError(it)
                })
            }
        }
    }

    companion object {
        private const val EXPIRE_TIME = 15 * 1000L
    }
}