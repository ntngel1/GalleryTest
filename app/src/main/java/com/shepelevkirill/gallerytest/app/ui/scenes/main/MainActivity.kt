package com.shepelevkirill.gallerytest.app.ui.scenes.main

import android.os.Bundle
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
import com.shepelevkirill.gallerytest.app.ui.scenes.upload.OnShowPhotoListener
import com.shepelevkirill.gallerytest.domain.models.PhotoModel
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : MvpAppCompatActivityX(), MainView, OnShowPhotoListener {
    @InjectPresenter
    lateinit var presenter: MainPresenter

    @ProvidePresenter
    fun provideMainPresenter(): MainPresenter {
        return App.appComponent.provideMainPresenter()
    }

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        return@OnNavigationItemSelectedListener presenter.onNavigationItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
    }

    override fun requestPermissions(vararg permissions: String) {
        Dexter.withActivity(this)
            .withPermissions(permissions.asList())
            .withListener(object: MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport?) {}
                override fun onPermissionRationaleShouldBeShown(permissions: MutableList<PermissionRequest>?, token: PermissionToken?) {}
            })
            .onSameThread()
            .check()
    }

    override fun setNavigationSelection(id: Int) {
        navigation.selectedItemId = id
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

    override fun onShowPhoto(photoModel: PhotoModel) {
        presenter.onShowPhoto(photoModel)
    }
}
