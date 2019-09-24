package com.newsbreak.test.di.module

import com.newsbreak.test.BuildConfig
import com.newsbreak.test.api.service.OpenApiService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {
    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()

        if (BuildConfig.DEBUG) {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        }

        return httpLoggingInterceptor
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(httpLoggingInterceptor)
        .connectTimeout(CLIENT_TIME_OUT_SECS, TimeUnit.SECONDS)
        .writeTimeout(CLIENT_TIME_OUT_SECS, TimeUnit.SECONDS)
        .readTimeout(CLIENT_TIME_OUT_SECS, TimeUnit.SECONDS)
        .build()

    @Singleton
    @Provides
    fun provideOpenApiService(client: OkHttpClient): OpenApiService = Retrofit.Builder()
        .baseUrl(OPEN_API_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(client)
        .build()
        .create(OpenApiService::class.java)

    companion object {
        private const val CLIENT_TIME_OUT_SECS = 60L
        private const val OPEN_API_BASE_URL = "http://openapi.particlenews.com/Website/openapi/"
    }
}