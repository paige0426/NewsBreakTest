package com.newsbreak.test.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.newsbreak.test.api.service.OpenApiService
import com.newsbreak.test.repository.NewsRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class MainViewModel @Inject constructor(private val newsRepository: NewsRepository): ViewModel() {
    private val mDisposable = CompositeDisposable()

    val newsList: MutableLiveData<List<OpenApiService.News>> = MutableLiveData()
    val errorMessage: MutableLiveData<String> = MutableLiveData()
    val isRefreshing: MutableLiveData<Boolean> = MutableLiveData()

    init {
        loadNews()
    }

    fun loadNews() {
        isRefreshing.postValue(true)

        val disposable = newsRepository.loadNews()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                newsList.postValue(it.newsList)
                isRefreshing.postValue(false)
            }, {
                Timber.e(it)
                errorMessage.postValue(it.toString())
                isRefreshing.postValue(false)
            })

        mDisposable.add(disposable)
    }

    override fun onCleared() {
        if (!mDisposable.isDisposed) {
            mDisposable.clear()
        }
    }
}