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



class MainActivity : AppCompatActivity() {

    private val newPhotosFragment = NewPhotosFragment.newInstance()
    private val popularPhotosFragment = PopularPhotosFragment.newInstance()
    private var currentFragment: PhotosFragment = newPhotosFragment

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_new -> currentFragment = newPhotosFragment
            R.id.navigation_popular -> currentFragment = popularPhotosFragment
            else -> return@OnNavigationItemSelectedListener  false
        }

        val cm = applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val netInfo = cm.activeNetworkInfo

        if (netInfo == null || !netInfo.isConnected) currentFragment.showNetworkErrorScreen()

        openFragment(currentFragment)

        return@OnNavigationItemSelectedListener true
    }

    private fun openFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(fragment_container.id, fragment)
            .commit()
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        openFragment(currentFragment)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        val permissions = RxPermissions(this)
        permissions.request(Manifest.permission.INTERNET, Manifest.permission.ACCESS_NETWORK_STATE)
            .subscribe()
    }
}
