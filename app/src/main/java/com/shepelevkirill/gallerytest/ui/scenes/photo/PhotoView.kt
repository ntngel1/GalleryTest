package com.shepelevkirill.gallerytest.ui.scenes.photo

import com.shepelevkirill.core.models.PhotoModel
import com.squareup.picasso.RequestCreator

interface PhotoView {

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