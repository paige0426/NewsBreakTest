package com.newsbreak.test.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import butterknife.BindView
import butterknife.ButterKnife
import com.newsbreak.test.R
import dagger.android.AndroidInjection

class WebViewActivity : AppCompatActivity() {
    @BindView(R.id.news_view)
    lateinit var newsView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)
        ButterKnife.bind(this)

        newsView.apply {
            webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                    if (url == null || url.startsWith("http:") || url.startsWith("https")) {
                        return false
                    }

                    return true
                }
            }
            webChromeClient = WebChromeClient()
            settings.javaScriptEnabled = true
            loadUrl(intent.data?.toString())
        }
    }
}