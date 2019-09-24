package com.newsbreak.test.di.component

import android.app.Application
import com.newsbreak.test.NewsBreakApplication
import com.newsbreak.test.di.module.ActivityModule
import com.newsbreak.test.di.module.AppModule
import com.newsbreak.test.di.module.NetworkModule
import com.newsbreak.test.di.module.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidInjectionModule::class,
    ActivityModule::class,
    AppModule::class,
    NetworkModule::class,
    ViewModelModule::class
])
interface AppComponent {
    fun inject(app: NewsBreakApplication)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}