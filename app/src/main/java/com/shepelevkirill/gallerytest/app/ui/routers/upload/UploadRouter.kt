package com.shepelevkirill.gallerytest.app.ui.routers.upload

import com.shepelevkirill.gallerytest.domain.models.PhotoModel

interface UploadRouter {

    fun openLoadingDialog()
    fun closeLoadingDialog()
    fun openPhotoUploadedDialog()
    fun closePhotoUploadedDialog()

    fun onHighlightPhoto(photo: PhotoModel)
}