package com.shepelevkirill.gallerytest.app.di.data.app

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(
    /*includes = [
        UseCaseModule::class*//*,
        RetrofitModule::class,
        RetrofitGatewayModule::class,
        AndroidGatewayModule::class*//*
    ]*/
)
class AppModule(private val app: Application) {

    @Singleton
    @Provides
    fun provideApplication(): Application = app

    @Singleton
    @Provides
    fun provideContext(app: Application): Context = app.applicationContext
}