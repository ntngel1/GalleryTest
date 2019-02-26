package com.shepelevkirill.gallerytest

import android.app.Application
import android.content.Context

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        App.appContext = applicationContext
    }

    companion object {

        var appContext: Context? = null
            private set
    }
}