package com.shepelevkirill.gallerytest.ui.scenes.main

import android.Manifest
import android.util.Log
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.shepelevkirill.gallerytest.R
import com.shepelevkirill.gallerytest.ui.scenes.photos.PhotosFragment
import com.shepelevkirill.gallerytest.ui.scenes.photos.PhotosView

@InjectViewState
class MainPresenter : MvpPresenter<MainView>() {
    private val newPhotosFragment = PhotosFragment.newInstance(true, false)
    private val popularPhotosFragment = PhotosFragment.newInstance(false, true)

    private lateinit var currentFragment: Fragment

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.requestPermissions(Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.INTERNET)
        viewState.openFragment(newPhotosFragment)
    }

    fun onNavigationItemSelected(item: MenuItem): Boolean {
        val newFragment: Fragment = when (item.itemId) {
            R.id.navigation_new -> newPhotosFragment
            R.id.navigation_popular -> popularPhotosFragment
            else -> return false
        }

        if (currentFragment != newFragment) {
            viewState.openFragment(newFragment)
        }

        return true
    }

}