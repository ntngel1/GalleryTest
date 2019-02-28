package com.shepelevkirill.gallerytest

import android.app.Application
import android.content.Context
import com.shepelevkirill.gallerytest.data.app.AppModule
import com.shepelevkirill.gallerytest.data.network.GatewayModule
import com.shepelevkirill.gallerytest.data.network.RetrofitModule

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        App.applicationContext = applicationContext
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule)
            .retrofitModule(RetrofitModule)
            .gatewayModule(GatewayModule)
            .build()
    }

    companion object {
        lateinit var appComponent: AppComponent
        var applicationContext: Context? = null
            private set
    }
}