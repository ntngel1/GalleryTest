package com.shepelevkirill.gallerytest.screens.main

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.shepelevkirill.gallerytest.screens.new_photos.NewPhotosView
import com.shepelevkirill.gallerytest.R
import kotlinx.android.synthetic.main.activity_main.*
import com.shepelevkirill.gallerytest.core.screens.Main
import com.shepelevkirill.gallerytest.scree.PopularPhotosView


class MainView : AppCompatActivity(), Main.View {
    private var presenter: Main.Presenter = MainPresenter()

    private val newPhotosFragment = NewPhotosView.newInstance()
    private val popularPhotosFragment = PopularPhotosView.newInstance()
    private var currentFragment: Fragment = newPhotosFragment

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        val newFragment: Fragment = when (item.itemId) {
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
