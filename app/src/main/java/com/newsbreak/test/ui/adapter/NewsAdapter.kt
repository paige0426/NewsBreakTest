package com.newsbreak.test.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.newsbreak.test.api.service.OpenApiService
import javax.inject.Inject

class NewsAdapter @Inject constructor() : RecyclerView.Adapter<NewsViewHolder>() {
    private val newsList: MutableList<OpenApiService.News> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = NewsViewHolder.create(parent)

    override fun getItemCount() = newsList.size

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) = holder.bind(newsList[position])

    fun updateNews(newsList: List<OpenApiService.News>) {
        this.newsList.clear()
        this.newsList.addAll(newsList)

        notifyDataSetChanged()
    }
}