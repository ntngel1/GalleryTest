package com.shepelevkirill.gallerytest.ui.scenes.main

import android.Manifest
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.shepelevkirill.gallerytest.ui.scenes.new_photos.NewPhotosView
import com.shepelevkirill.gallerytest.ui.scenes.popular_photos.PopularPhotosView
import com.tbruyelle.rxpermissions2.RxPermissions

class MainPresenter : MainView.Presenter {
    private var view: MainView.View? = null

    override fun attachView(view: MainView.View) {
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
            is NewPhotosView.View -> {
                (newFragment as NewPhotosView.View).onOpen()
            }
            is PopularPhotosView.View -> (newFragment as PopularPhotosView.View).onOpen()
        }
    }

}