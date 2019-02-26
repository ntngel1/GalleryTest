package com.shepelevkirill.gallerytest.screens.main

import android.Manifest
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.shepelevkirill.gallerytest.screens.new_photos.NewPhotos
import com.shepelevkirill.gallerytest.screens.popular_photos.PopularPhotos
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
        Log.d("Fragment CHANGEd", "YYYEEE")
        when (newFragment) {
            is NewPhotos.View -> {
                (newFragment as NewPhotos.View).onOpen()
            }
            is PopularPhotos.View -> (newFragment as PopularPhotos.View).onOpen()
        }
    }

}