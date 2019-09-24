package com.newsbreak.test.ui.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.bumptech.glide.Glide
import com.newsbreak.test.R
import com.newsbreak.test.api.service.OpenApiService

class NewsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    @BindView(R.id.date_text)
    lateinit var date: TextView

    @BindView(R.id.source_text)
    lateinit var source: TextView

    @BindView(R.id.news_title)
    lateinit var title: TextView

    @BindView(R.id.news_image)
    lateinit var image: ImageView

    private var url: String? = null

    init {
        ButterKnife.bind(this, view)

        view.setOnClickListener {
            if (!url.isNullOrEmpty()) {
                CustomTabsIntent.Builder()
                    .setShowTitle(true)
                    .build()
                    .launchUrl(it.context, Uri.parse(url))
            }
        }
    }

    fun bind(news: OpenApiService.News) {
        date.text = news.date
        source.text = news.source
        title.text = news.title
        url = news.url

        Glide.with(image.context)
            .load(IMAGE_BASE_URL + news.image)
            .centerCrop()
            .into(image)
    }

    companion object {
        private const val IMAGE_BASE_URL = "https://img.particlenews.com/image.php?url="

        fun create(parent: ViewGroup): NewsViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.news_card, parent, false)

            return NewsViewHolder(view)
        }
    }
}