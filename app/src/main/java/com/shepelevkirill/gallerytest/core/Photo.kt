package com.shepelevkirill.gallerytest.core

interface Photo {

    interface View {
    }

    interface Presenter {
        fun attachView(view: View)
        fun detachView()
    }

}