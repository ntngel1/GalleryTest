package com.shepelevkirill.gallerytest.ui.scenes.main

import androidx.fragment.app.Fragment
import com.arellomobile.mvp.MvpView

interface MainView : MvpView {
    fun requestPermissions(vararg permissions: String)
    fun openFragment(fragment: Fragment)
}