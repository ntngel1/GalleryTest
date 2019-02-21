package com.shepelevkirill.gallerytest

import android.Manifest
import android.content.Context
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.activity_main.*
import android.net.ConnectivityManager
import com.shepelevkirill.gallerytest.core.Main


class MainActivity : AppCompatActivity(), Main.View {
    private lateinit var presenter: Main.Presenter

    private val newPhotosFragment = NewPhotosFragment.newInstance()
    private val popularPhotosFragment = PopularPhotosFragment.newInstance()
    private var currentFragment: Fragment = newPhotosFragment

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        val newFragment = when (item.itemId) {
            R.id.navigation_new -> newPhotosFragment
            R.id.navigation_popular -> popularPhotosFragment
            else -> return@OnNavigationItemSelectedListener  false
        }

        presenter.onFragmentChanged(newFragment, currentFragment)

        openFragment(newFragment)

        return@OnNavigationItemSelectedListener true
    }

    private fun openFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(fragment_container.id, fragment)
            .commit()

        currentFragment = fragment
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        openFragment(newPhotosFragment)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        presenter.attachView(this)
        presenter.onCreate()
    }

    override fun onDestroy() {
        super.onDestroy()

        presenter.detachView()
    }
}
