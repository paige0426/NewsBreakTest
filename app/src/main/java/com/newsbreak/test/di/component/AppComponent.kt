package com.newsbreak.test.di.component

import android.app.Application
import com.newsbreak.test.NewsBreakApplication
import com.newsbreak.test.di.module.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidInjectionModule::class,
    ActivityModule::class,
    AppModule::class,
    DatabaseModule::class,
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