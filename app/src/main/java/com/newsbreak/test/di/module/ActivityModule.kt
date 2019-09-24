package com.newsbreak.test.di.module

import com.newsbreak.test.di.scope.ActivityScope
import com.newsbreak.test.ui.MainActivity
import com.newsbreak.test.ui.WebViewActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {
    @ContributesAndroidInjector
    @ActivityScope
    abstract fun provideMainActivityContributor(): MainActivity

    @ContributesAndroidInjector
    @ActivityScope
    abstract fun provideWebViewActivityContributor(): WebViewActivity
}