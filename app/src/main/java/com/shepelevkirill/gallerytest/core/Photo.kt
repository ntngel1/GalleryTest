package com.shepelevkirill.gallerytest.core

import android.graphics.Bitmap
import android.widget.ImageView
import com.shepelevkirill.core.models.PhotoModel
import com.squareup.picasso.Picasso

interface Photo {

    interface View {
        fun getPhotoModel(): PhotoModel
        fun showPhoto(photo: Bitmap)
    }

    interface Presenter {
        fun attachView(view: View)
        fun detachView()

        fun onViewCreated()
    }

}