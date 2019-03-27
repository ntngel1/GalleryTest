package com.shepelevkirill.gallerytest.ui.scenes.photo

import android.widget.ImageView
import com.arellomobile.mvp.MvpView
import com.shepelevkirill.core.models.PhotoModel
import com.squareup.picasso.RequestCreator

interface PhotoView : MvpView {
    fun showImage(url: String)
    fun getPhotoModel()
}