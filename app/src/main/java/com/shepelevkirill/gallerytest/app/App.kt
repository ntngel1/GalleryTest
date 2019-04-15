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
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        initCicerone()
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .retrofitModule(RetrofitModule)
            .retrofitGatewayModule(RetrofitGatewayModule)
            .androidGatewayModule(AndroidGatewayModule)
            .useCaseModule(UseCaseModule())
            .build()
    }

    private fun initCicerone() {
        cicerone = Cicerone.create()
    }

    companion object {
        lateinit var appComponent: AppComponent
        lateinit var appContext: Context

        val router: Router
            get() = cicerone.router
        val navigatorHolder: NavigatorHolder
            get() = cicerone.navigatorHolder

        private lateinit var cicerone: Cicerone<Router>
    }
}