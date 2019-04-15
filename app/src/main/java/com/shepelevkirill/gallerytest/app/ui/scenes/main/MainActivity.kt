package com.shepelevkirill.gallerytest.app.ui.scenes.main

import android.Manifest
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.arellomobile.moxy.MvpAppCompatActivityX
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.shepelevkirill.gallerytest.R
import com.shepelevkirill.gallerytest.app.App
import com.shepelevkirill.gallerytest.app.ui.scenes.authentication.AuthenticationFragment
import com.shepelevkirill.gallerytest.app.ui.scenes.photos.PhotosFragment
import com.shepelevkirill.gallerytest.app.ui.scenes.upload.OnShowPhotoListener
import com.shepelevkirill.gallerytest.app.ui.scenes.upload.UploadFragment
import com.shepelevkirill.gallerytest.domain.models.PhotoModel
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : MvpAppCompatActivityX(), MainView, OnShowPhotoListener {

    private lateinit var currentFragment: Fragment
    private val newPhotosFragment = PhotosFragment.newInstance(true, false, "New")
    private val popularPhotosFragment = PhotosFragment.newInstance(false, true, "Popular")
    private val uploadFragment = UploadFragment.newInstance()
    private val authenticationFragment = AuthenticationFragment.newInstance()
    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        return@OnNavigationItemSelectedListener onNavigationItemSelected(item)
    }

    @InjectPresenter
    lateinit var presenter: MainPresenter

    @ProvidePresenter
    fun provideMainPresenter(): MainPresenter {
        return App.appComponent.provideMainPresenter()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        requestPermissions()
        navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
    }

    override fun openDefaultScreen() {
        openScreen(newPhotosFragment)
        currentFragment = newPhotosFragment
    }

    private fun requestPermissions() {
        Dexter.withActivity(this)
            .withPermissions(
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.INTERNET,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
            .withListener(object: MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport?) {}
                override fun onPermissionRationaleShouldBeShown(permissions: MutableList<PermissionRequest>?, token: PermissionToken?) {}
            })
            .onSameThread()
            .check()
    }

    private fun onNavigationItemSelected(item: MenuItem): Boolean {
        val newFragment: Fragment = when (item.itemId) {
            R.id.navigation_new -> newPhotosFragment
            R.id.navigation_popular -> popularPhotosFragment
            R.id.navigation_upload -> uploadFragment
            R.id.navigation_authentication -> authenticationFragment
            else -> return false
        }

        if (currentFragment != newFragment) {
            openScreen(newFragment)
            currentFragment = newFragment
        }

        return true
    }

    override fun openScreen(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
        supportFragmentManager.executePendingTransactions()
    }

    override fun openScreenWithBackStack(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun popFragment() {
        supportFragmentManager.popBackStack()
    }

    override fun openNewPhotosView() {
        navigation.selectedItemId = R.id.navigation_new
    }

    override fun onShowPhoto(photoModel: PhotoModel) {
        openNewPhotosView()
        newPhotosFragment.presenter.onHighlightPhoto(photoModel)
    }
}
