package com.shepelevkirill.gallerytest.app.di.data.app

import android.content.Context
import com.shepelevkirill.gallerytest.app.App
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val app: App) {

    @Singleton
    @Provides
    fun provideApplication(): App = app

    @Singleton
    @Provides
    fun provideContext(app: App): Context = app.applicationContext
}