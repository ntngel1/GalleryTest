package com.shepelevkirill.gallerytest.ui.scenes.photo

import com.shepelevkirill.core.gateway.PhotoGateway
import com.shepelevkirill.core.models.PhotoModel
import com.shepelevkirill.gallerytest.App
import com.shepelevkirill.gallerytest.utils.load
import com.squareup.picasso.Picasso
import javax.inject.Inject

class PhotoPresenter : PhotoView.Presenter {
    private var view: PhotoView.View? = null
    private var photoModel: PhotoModel? = null
    @Inject lateinit var photoGateway: PhotoGateway

    init {
        App.appComponent.inject(this)
    }

    override fun onViewCreated() {
        photoModel = view?.getPhotoModel()
        showPhoto()
    }

    private fun showPhoto() {
        val url = photoGateway.getPhotoUrl(photoModel!!.image.contentUrl)
        view?.getImageView()?.load(url)
    }

    override fun attachView(view: PhotoView.View) {
        this.view = view
    }

    override fun detachView() {
        view = null
    }
}