package com.shepelevkirill.gallerytest.data.app

import android.content.Context
import com.shepelevkirill.gallerytest.App
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object AppModule {

    @Singleton
    @Provides
    fun provideApplication(): App.Companion = App

    @Singleton
    @Provides
    fun provideContext(app: App.Companion): Context = app.applicationContext!!
}