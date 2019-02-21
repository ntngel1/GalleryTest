package com.shepelevkirill.gallerytest.core

import com.shepelevkirill.core.models.PhotoModel

interface PopularPhotos {

    interface View {
        fun showNetworkError()
    }

    interface Presenter {
        fun attachView(view: View)
        fun detachView()

        fun onPhotoClicked(photo: PhotoModel)
    }

}