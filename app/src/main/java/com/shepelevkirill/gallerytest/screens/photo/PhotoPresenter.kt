package com.shepelevkirill.gallerytest.screens.photo

import com.squareup.picasso.Picasso

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