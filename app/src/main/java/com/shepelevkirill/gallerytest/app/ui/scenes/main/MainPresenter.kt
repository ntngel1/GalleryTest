package com.shepelevkirill.gallerytest.app.ui.scenes.main

import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.shepelevkirill.gallerytest.R
import com.shepelevkirill.gallerytest.app.ui.scenes.authentication.AuthenticationFragment
import com.shepelevkirill.gallerytest.app.ui.scenes.photos.PhotosFragment
import com.shepelevkirill.gallerytest.app.ui.scenes.upload.UploadFragment
import com.shepelevkirill.gallerytest.domain.models.PhotoModel
import javax.inject.Inject

@InjectViewState
class MainPresenter @Inject constructor() : MvpPresenter<MainView>() {
    // TODO Move to router class
    private val newPhotosFragment = PhotosFragment.newInstance(true, false, "New")
    private val popularPhotosFragment = PhotosFragment.newInstance(false, true, "Popular")
    private val uploadFragment = UploadFragment.newInstance()
    private val authenticationFragment = AuthenticationFragment.newInstance()
    private lateinit var currentFragment: Fragment


    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        openDefaultFragment()
    }

    private fun openDefaultFragment() {
        viewState.openScreen(newPhotosFragment)
        currentFragment = newPhotosFragment
    }

    fun onNavigationItemSelected(item: MenuItem): Boolean {
        val newFragment: Fragment = when (item.itemId) {
            R.id.navigation_new -> newPhotosFragment
            R.id.navigation_popular -> popularPhotosFragment
            R.id.navigation_upload -> uploadFragment
            R.id.navigation_authentication -> authenticationFragment
            else -> return false
        }

        if (currentFragment != newFragment) {
            viewState.openScreen(newFragment)
            currentFragment = newFragment
        }

        return true
    }

    fun onShowPhoto(photoModel: PhotoModel) {
        viewState.setNavigationSelection(R.id.navigation_new)
        newPhotosFragment.onHighlightPhoto(photoModel)
    }
}