package com.shepelevkirill.gallerytest.ui.scenes.photo

import android.widget.ImageView
import com.shepelevkirill.core.models.PhotoModel
import com.squareup.picasso.RequestCreator

interface PhotoView {

    interface View {
        fun getPhotoModel(): PhotoModel
        fun getImageView(): ImageView
    }

    interface Presenter {
        fun attachView(view: View)
        fun detachView()

        fun onViewCreated()
    }

}