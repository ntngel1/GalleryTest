package com.shepelevkirill.gallerytest.ui.scenes.main

import android.Manifest
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.arellomobile.mvp.MvpActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.shepelevkirill.gallerytest.R
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : MvpActivity(), MainView {
    @InjectPresenter
    lateinit var presenter: MainPresenter

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

    override fun openFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(fragment_container.id, fragment)
            .commit()
    }
}
