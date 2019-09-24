package com.newsbreak.test.ui

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import butterknife.BindView
import butterknife.ButterKnife
import com.newsbreak.test.R
import com.newsbreak.test.ui.adapter.NewsAdapter
import com.newsbreak.test.viewmodel.MainViewModel
import com.newsbreak.test.viewmodel.ViewModelFactory
import dagger.android.AndroidInjection
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    @BindView(R.id.news_list)
    lateinit var newsList: RecyclerView

    @BindView(R.id.swipe_layout)
    lateinit var swipeLayout: SwipeRefreshLayout

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var newsAdapter: NewsAdapter

    private val mainViewModel: MainViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)

        newsList.apply {
            layoutManager = LinearLayoutManager(context).apply {
                orientation = if(isScreenPortrait()) RecyclerView.VERTICAL else RecyclerView.HORIZONTAL
            }

            adapter = newsAdapter
        }

        swipeLayout.setOnRefreshListener {
            mainViewModel.loadNews()
        }

        mainViewModel.isRefreshing.observe(this, Observer {
            swipeLayout.isRefreshing = it
        })

        mainViewModel.newsList.observe(this, Observer {
            newsAdapter.updateNews(it)
        })

        mainViewModel.errorMessage.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        })
    }

    private fun isScreenPortrait() = resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT
}
