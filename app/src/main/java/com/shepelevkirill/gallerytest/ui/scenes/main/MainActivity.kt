package com.shepelevkirill.gallerytest.ui.scenes.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.shepelevkirill.gallerytest.R
import com.shepelevkirill.gallerytest.ui.scenes.photos.PhotosFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), MainView.View {
    private var presenter: MainView.Presenter = MainPresenter()

    private val newPhotosFragment = PhotosFragment.newInstance(true, null)
    private val popularPhotosFragment = PhotosFragment.newInstance(null, true)
    private var currentFragment: Fragment = newPhotosFragment

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        val newFragment: Fragment = when (item.itemId) {
            R.id.navigation_new -> newPhotosFragment
            R.id.navigation_popular -> popularPhotosFragment
            else -> return@OnNavigationItemSelectedListener false
        }

        openFragment(newFragment)

        return@OnNavigationItemSelectedListener true
    }

    private fun openFragment(fragment: Fragment) {
        presenter.onFragmentChanged(fragment, currentFragment)
        supportFragmentManager.beginTransaction()
            .replace(fragment_container.id, fragment)
            .commit()

        currentFragment = fragment
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        openFragment(newPhotosFragment)
        navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        presenter.attachView(this)
        presenter.onCreate()
    }

    override fun onDestroy() {
        super.onDestroy()

        presenter.detachView()
    }
}
