package com.shepelevkirill.gallerytest.screens.main

import android.Manifest
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.shepelevkirill.gallerytest.core.Main
import com.tbruyelle.rxpermissions2.RxPermissions

class MainPresenter : Main.Presenter {
    private var view: Main.View? = null

    override fun attachView(view: Main.View) {
        this.view = view
    }

    override fun detachView() {
        view = null
    }

    override fun onCreate() {
        val permissions = RxPermissions(view as FragmentActivity)
        permissions.request(Manifest.permission.INTERNET, Manifest.permission.ACCESS_NETWORK_STATE)
            .subscribe()
    }

    override fun onFragmentChanged(newFragment: Fragment, oldFragment: Fragment) {
        TODO("Check internet connection for not to flash")
        /*
        val activity = view as FragmentActivity
        if (newFragment is Photos.View) {
            val cm = activity.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val netInfo = cm.activeNetworkInfo
            if (netInfo == null || !netInfo.isConnected)
        }
        */
    }
}