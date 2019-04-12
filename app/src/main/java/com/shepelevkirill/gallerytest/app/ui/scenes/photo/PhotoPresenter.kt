package com.shepelevkirill.gallerytest.app.ui.scenes.photo

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.shepelevkirill.gallerytest.domain.gateway.PhotoGateway
import com.shepelevkirill.gallerytest.domain.models.PhotoModel
import javax.inject.Inject

@InjectViewState
class PhotoPresenter @Inject constructor(
    private val photoGateway: PhotoGateway
) : MvpPresenter<PhotoView>() {

    private var photoModel: PhotoModel? = null


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