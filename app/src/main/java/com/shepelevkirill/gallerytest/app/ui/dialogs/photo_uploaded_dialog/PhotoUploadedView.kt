package com.shepelevkirill.gallerytest.app.ui.dialogs.photo_uploaded_dialog

import com.arellomobile.mvp.MvpView
import com.shepelevkirill.gallerytest.domain.models.PhotoModel


interface PhotoUploadedView : MvpView {
    fun setPhotoModel(photoModel: PhotoModel)

    fun close()
}