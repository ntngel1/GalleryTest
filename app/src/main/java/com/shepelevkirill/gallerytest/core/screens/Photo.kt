package com.shepelevkirill.gallerytest.core.screens

import com.shepelevkirill.core.models.PhotoModel
import com.squareup.picasso.RequestCreator

interface Photo {

    interface View {
        fun getPhotoModel(): PhotoModel
        fun showPhoto(picasso: RequestCreator)
    }

    interface Presenter {
        fun attachView(view: View)
        fun detachView()

        fun onViewCreated()
    }

}