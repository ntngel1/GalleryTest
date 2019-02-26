package com.shepelevkirill.gallerytest

import android.app.Application
import android.content.Context
import com.shepelevkirill.gallerytest.data.GatewayModule
import com.shepelevkirill.gallerytest.data.RetrofitModule

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        App.applicationContext = applicationContext
        appComponent = DaggerAppComponent.builder()
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