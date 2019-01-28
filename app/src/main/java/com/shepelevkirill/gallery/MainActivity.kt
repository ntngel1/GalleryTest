package com.shepelevkirill.gallery

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val newPhotosFragment = NewPhotosFragment.newInstance()
    private val popularPhotoFragment = PopularPhotosFragment.newInstance()
    private var currentFragment: Fragment = newPhotosFragment

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
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction()
            .hide(currentFragment)
            .show(fragment)
            .commit()

        currentFragment = fragment
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .add(fragment_container.id, newPhotosFragment, "newPhotos").hide(newPhotosFragment)
            .add(fragment_container.id, popularPhotoFragment, "popularPhotos").hide(popularPhotoFragment)
            .show(currentFragment)
            .commit()

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }
}
