package com.shepelevkirill.gallerytest.screens.photo

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import com.shepelevkirill.core.models.PhotoModel
import com.shepelevkirill.gallerytest.core.Photo
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import java.lang.Exception

class PhotoPresenter : Photo.Presenter {
    private var view: Photo.View? = null

    override fun onViewCreated() {
        val photoModel = view?.getPhotoModel()

        loadPhoto(photoModel!!)
    }

    private fun loadPhoto(photo: PhotoModel) {
        Picasso.get()
            .load(photo.image.contentUrl)
            .into(object : Target {
                override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                    view?.showPhoto(bitmap!!)
                }

            })
    }

    override fun attachView(view: Photo.View) {
        this.view = view
    }

    override fun detachView() {
        view = null
    }
}