package com.shepelevkirill.gallerytest.screens.photo

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import com.shepelevkirill.core.models.PhotoModel
import com.shepelevkirill.gallerytest.core.screens.Photo
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import java.lang.Exception

class PhotoPresenter : Photo.Presenter {
    private var view: Photo.View? = null

    override fun onViewCreated() {
        showPhoto()
    }

    private fun showPhoto() {
        val picasso = Picasso.get()
            .load("http://gallery.dev.webant.ru/media/${view!!.getPhotoModel().image.contentUrl}")
        view!!.showPhoto(picasso)
    }

    override fun attachView(view: Photo.View) {
        this.view = view
    }

    override fun detachView() {
        view = null
    }
}