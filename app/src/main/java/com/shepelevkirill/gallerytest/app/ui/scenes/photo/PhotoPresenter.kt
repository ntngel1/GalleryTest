package com.shepelevkirill.gallerytest.app.ui.scenes.photo

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.shepelevkirill.gallerytest.domain.gateway.PhotoGateway
import com.shepelevkirill.gallerytest.domain.models.PhotoModel
import com.shepelevkirill.gallerytest.app.App
import javax.inject.Inject

@InjectViewState
class PhotoPresenter : MvpPresenter<PhotoView>() {
    @Inject lateinit var photoGateway: PhotoGateway

    private var photoModel: PhotoModel? = null

    init {
        App.appComponent.inject(this)
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.getPhotoModel()
        showPhoto()
    }

    fun onGetPhotoModel(photoModel: PhotoModel?) {
        this.photoModel = photoModel
    }

    private fun showPhoto() {
        val url = photoGateway.getPhotoUrl(photoModel!!.image.contentUrl)
        viewState.showPhoto(url)
    }
}