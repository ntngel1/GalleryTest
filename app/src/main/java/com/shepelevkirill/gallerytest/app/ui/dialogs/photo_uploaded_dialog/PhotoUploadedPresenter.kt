package com.shepelevkirill.gallerytest.app.ui.dialogs.photo_uploaded_dialog

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.shepelevkirill.gallerytest.domain.models.PhotoModel

@InjectViewState
class PhotoUploadedPresenter : MvpPresenter<PhotoUploadedView>() {

    lateinit var photoModel: PhotoModel
    var onShowClickedListener: (() -> Unit)? = null
    var onOkClickedListener: (() -> Unit)? = null


    fun onShowButtonClicked() {
        viewState.close()
        onShowClickedListener?.invoke()
    }

    fun onOkButtonClicked() {
        viewState.close()
        onOkClickedListener?.invoke()
    }
}