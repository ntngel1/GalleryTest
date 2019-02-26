package com.shepelevkirill.gallerytest.ui.scenes.main

import androidx.fragment.app.Fragment

interface MainView {
    interface View {
    }

    interface Presenter {
        fun attachView(view: View)
        fun detachView()

        fun onCreate()

        fun onFragmentChanged(newFragment: Fragment, oldFragment: Fragment)
    }
}