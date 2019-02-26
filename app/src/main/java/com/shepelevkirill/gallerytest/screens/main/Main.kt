package com.shepelevkirill.gallerytest.screens.main

import androidx.fragment.app.Fragment

interface Main {
    interface View {
    }

    interface Presenter {
        fun attachView(view: View)
        fun detachView()

        fun onCreate()

        fun onFragmentChanged(newFragment: Fragment, oldFragment: Fragment)
    }
}