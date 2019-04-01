package com.shepelevkirill.gallerytest.domain.usecases.photos

import com.shepelevkirill.gallerytest.domain.gateway.AuthenticationGateway
import com.shepelevkirill.gallerytest.domain.gateway.ClientGateway
import com.shepelevkirill.gallerytest.domain.gateway.MediaObjectGateway
import com.shepelevkirill.gallerytest.domain.gateway.PhotoGateway
import com.shepelevkirill.gallerytest.domain.models.PhotoCreateRequestModel
import com.shepelevkirill.gallerytest.domain.models.PhotoModel
import com.shepelevkirill.gallerytest.domain.usecases.base.CompletableUseCase
import com.shepelevkirill.gallerytest.domain.usecases.base.SingleUseCase
import io.reactivex.Single
import java.io.File
import javax.inject.Inject

class UploadPhotoUseCase : SingleUseCase<UploadPhotoUseCase.Params, PhotoModel> {
    data class Params(val title: String, val description: String, val file: File)

    lateinit var mediaObjectGateway: MediaObjectGateway
    lateinit var photoGateway: PhotoGateway

    @Inject
    constructor(mediaObjectGateway: MediaObjectGateway, photoGateway: PhotoGateway) : super() {
        this.mediaObjectGateway = mediaObjectGateway
        this.photoGateway = photoGateway
    }

    override fun build(param: Params): Single<PhotoModel> {
        return mediaObjectGateway.uploadPhoto(param.file)
            .flatMap { mediaObject ->
                val request = PhotoCreateRequestModel(
                    param.title,
                    param.description,
                    true,
                    false,
                    mediaObjectGateway.getMediaObjectPathById(mediaObject.id)
                )
                photoGateway.createPhoto(request)
            }
    }
}