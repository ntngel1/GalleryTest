package com.shepelevkirill.gallerytest.app

import android.app.Application
import android.content.Context
import com.shepelevkirill.gallerytest.app.di.AppComponent
import com.shepelevkirill.gallerytest.app.di.DaggerAppComponent
import com.shepelevkirill.gallerytest.app.di.data.app.AppModule
import com.shepelevkirill.gallerytest.app.di.data.common.CommonGatewayModule
import com.shepelevkirill.gallerytest.app.di.data.server.RetrofitModule
import com.shepelevkirill.gallerytest.app.di.data.server.ServerGatewayModule
import com.shepelevkirill.gallerytest.domain.models.SessionModel

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        //LeakCanary.install(this);
        Companion.applicationContext = applicationContext
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule)
            .retrofitModule(RetrofitModule)
            .serverGatewayModule(ServerGatewayModule)
            .commonGatewayModule(CommonGatewayModule)
            .build()
    }

    companion object {
        lateinit var appComponent: AppComponent
        var applicationContext: Context? = null
            private set
        var session: SessionModel? = null
    }
}