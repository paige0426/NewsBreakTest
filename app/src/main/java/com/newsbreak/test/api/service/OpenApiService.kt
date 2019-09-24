package com.newsbreak.test.api.service

import com.google.gson.annotations.SerializedName
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenApiService {
    @GET("NewsListForHotChannelAction.php")
    fun loadNews(
        @Query("app") app: String = APP,
        @Query("token") token: String = TOKEN,
        @Query("cend") cend: String = CEND,
        @Query("cstart") cstart: String = CSTART
    ): Single<Response>

    data class Response(
        @SerializedName("result")
        val newsList: List<News>
    )

    data class News(
        val date: String,
        val source: String,
        val title: String,
        val image: String,
        val url: String
    )

    companion object {
        private const val APP = "cm"
        private const val TOKEN = "NmMzYThkM2Q4"
        private const val CEND = "500"
        private const val CSTART = "0"
    }
}