package com.shepelevkirill.gallerytest.screens.Main

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.shepelevkirill.gallerytest.NewPhotosView
import com.shepelevkirill.gallerytest.R
import kotlinx.android.synthetic.main.activity_main.*
import com.shepelevkirill.gallerytest.core.Main
import com.shepelevkirill.gallerytest.screens.PopularPhotos.PopularPhotosView


class MainView : AppCompatActivity(), Main.View {
    private lateinit var presenter: Main.Presenter

    private val newPhotosFragment = NewPhotosView.newInstance()
    private val popularPhotosFragment = PopularPhotosView.newInstance()
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
