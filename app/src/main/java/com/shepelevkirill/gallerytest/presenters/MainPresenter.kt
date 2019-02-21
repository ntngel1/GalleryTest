package com.shepelevkirill.gallerytest.presenters

import android.Manifest
import android.content.Context
import android.net.ConnectivityManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.shepelevkirill.gallerytest.core.Main
import com.shepelevkirill.gallerytest.core.NewPhotos
import com.shepelevkirill.gallerytest.core.PopularPhotos
import com.tbruyelle.rxpermissions2.RxPermissions

class MainPresenter : Main.Presenter {
    private var view: Main.View? = null

    override fun attachView(view: Main.View) {
        this.view = view
    }

    override fun detachView() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreate() {
        val permissions = RxPermissions(view as FragmentActivity)
        permissions.request(Manifest.permission.INTERNET, Manifest.permission.ACCESS_NETWORK_STATE)
            .subscribe()
    }

    override fun onFragmentChanged(newFragment: Fragment, oldFragment: Fragment) {
        /*
        val activity = view as FragmentActivity
        if (newFragment is NewPhotos.View || newFragment is PopularPhotos.View) {
            val cm = activity.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val netInfo = cm.activeNetworkInfo
            if (netInfo == null || !netInfo.isConnected)
        }
        */
    }
}