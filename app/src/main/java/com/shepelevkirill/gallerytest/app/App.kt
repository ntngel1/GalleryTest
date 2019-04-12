package com.shepelevkirill.gallerytest.app

import android.app.Application
import android.content.Context
import com.shepelevkirill.gallerytest.app.di.AppComponent
import com.shepelevkirill.gallerytest.app.di.DaggerAppComponent
import com.shepelevkirill.gallerytest.app.di.data.android.AndroidGatewayModule
import com.shepelevkirill.gallerytest.app.di.data.app.AppModule
import com.shepelevkirill.gallerytest.app.di.data.domain.UseCaseModule
import com.shepelevkirill.gallerytest.app.di.data.retrofit.RetrofitGatewayModule
import com.shepelevkirill.gallerytest.app.di.data.retrofit.RetrofitModule

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .retrofitModule(RetrofitModule)
            .retrofitGatewayModule(RetrofitGatewayModule)
            .androidGatewayModule(AndroidGatewayModule)
            .useCaseModule(UseCaseModule())
            .build()
    }


    companion object {
        lateinit var appComponent: AppComponent
        lateinit var appContext: Context
    }
}