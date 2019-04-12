package com.shepelevkirill.gallerytest.domain.usecases.impl.photos

import com.shepelevkirill.gallerytest.domain.gateway.MediaObjectGateway
import com.shepelevkirill.gallerytest.domain.gateway.PhotoGateway
import com.shepelevkirill.gallerytest.domain.models.PhotoCreateRequestModel
import com.shepelevkirill.gallerytest.domain.models.PhotoModel
import com.shepelevkirill.gallerytest.domain.usecases.core.photos.UploadPhotoUseCase
import io.reactivex.Single
import java.io.File

class UploadPhotoUseCase (private val photoGateway: PhotoGateway, private val mediaObjectGateway: MediaObjectGateway) : UploadPhotoUseCase {

    override fun uploadPhoto(title: String, description: String, file: File): Single<PhotoModel> = Single.defer {
        mediaObjectGateway.uploadPhoto(file)
            .flatMap { mediaObject ->
                val photoUri = mediaObjectGateway.getMediaObjectPathById(mediaObject.id)
                val requestModel = PhotoCreateRequestModel(title, description, true, false, photoUri)
                photoGateway.createPhoto(requestModel)
            }
    }
}