package com.shepelevkirill.gallerytest.ui.scenes.photo

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.shepelevkirill.core.gateway.PhotoGateway
import com.shepelevkirill.core.models.PhotoModel
import com.shepelevkirill.gallerytest.App
import javax.inject.Inject

@InjectViewState
class PhotoPresenter : MvpPresenter<PhotoView>() {
    private var photoModel: PhotoModel? = null
    @Inject lateinit var photoGateway: PhotoGateway

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
        viewState.showImage(url)
    }
}