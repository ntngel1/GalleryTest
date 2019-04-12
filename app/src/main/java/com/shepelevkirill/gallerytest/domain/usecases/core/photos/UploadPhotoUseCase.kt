package com.shepelevkirill.gallerytest.domain.usecases.core.photos

import com.shepelevkirill.gallerytest.domain.models.PhotoModel
import io.reactivex.Single
import java.io.File

interface UploadPhotoUseCase {

    fun uploadPhoto(title: String, description: String, file: File): Single<PhotoModel>
}