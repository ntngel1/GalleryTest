package com.shepelevkirill.gallerytest.app.ui.routers.main

import com.shepelevkirill.gallerytest.domain.models.PhotoModel

interface MainRouter {

    fun openPhotoView(photo: PhotoModel)
    fun openNewPhotosView()
    fun openPopularPhotosView()
    fun openAuthenticationView()
    fun openUploadPhotoView()
}