package com.shepelevkirill.gallerytest.app.ui.scenes.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.arellomobile.mvp.MvpActivityX
import com.arellomobile.mvp.presenter.InjectPresenter
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.shepelevkirill.gallerytest.R
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : MvpActivityX(), MainView {
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

    override fun openScreen(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
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
}
