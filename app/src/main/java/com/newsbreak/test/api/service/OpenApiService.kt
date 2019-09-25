package com.newsbreak.test.api.service

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import io.reactivex.Maybe
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenApiService {
    @GET("NewsListForHotChannelAction.php")
    fun loadNews(
        @Query("app") app: String = APP,
        @Query("token") token: String = TOKEN,
        @Query("cend") cend: String = CEND,
        @Query("cstart") cstart: String = CSTART
    ): Maybe<Response>

    data class Response(
        @SerializedName("result")
        val newsList: List<News>
    )

    @Entity(tableName = "news_table")
    data class News(
        @PrimaryKey
        @ColumnInfo(name = "id")
        val docid: String,
        @ColumnInfo(name = "date")
        val date: String,
        @ColumnInfo(name = "source")
        val source: String,
        @ColumnInfo(name = "title")
        val title: String,
        @ColumnInfo(name = "image")
        val image: String,
        @ColumnInfo(name = "url")
        val url: String
    )

    companion object {
        private const val APP = "cm"
        private const val TOKEN = "NmMzYThkM2Q4"
        private const val CEND = "500"
        private const val CSTART = "0"
    }
}