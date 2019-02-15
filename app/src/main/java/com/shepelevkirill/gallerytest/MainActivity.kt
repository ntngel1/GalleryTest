package com.shepelevkirill.gallerytest

import android.Manifest
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val newPhotosFragment = NewPhotosFragment.newInstance()
    private val popularPhotoFragment = PopularPhotosFragment.newInstance()

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_new -> {
                openFragment(newPhotosFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_popular -> {
                openFragment(popularPhotoFragment)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private fun openFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(fragment_container.id, fragment)
            .commit()
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        openFragment(newPhotosFragment)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        
        val permissions = RxPermissions(this)
        permissions.request(Manifest.permission.INTERNET)
            .subscribe()
    }
}
