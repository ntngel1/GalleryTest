package com.shepelevkirill.gallerytest.app.ui.scenes.upload

import com.shepelevkirill.gallerytest.domain.models.PhotoModel

interface OnShowPhotoListener {
    fun onShowPhoto(photoModel: PhotoModel)
}