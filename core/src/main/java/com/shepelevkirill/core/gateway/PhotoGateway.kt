package com.shepelevkirill.core.gateway

import com.shepelevkirill.core.models.*
import io.reactivex.Single

interface PhotoGateway {
    fun getPhotos(page: Int, limit: Int, new: Boolean?, popular: Boolean?): Single<PhotosModel>
    fun getPhotosData(new: Boolean, popular: Boolean): Single<PhotosModel>

    fun getPhotoUrl(image: String): String

    fun createPhoto(photo: PhotoCreateRequestModel): Single<PhotoModel>
    fun getPhoto(id: Int): Single<PhotoModel>
    fun updatePhoto(id: Int, photo: PhotoUpdateRequestModel): Single<PhotoModel>
    fun replacePhoto(id: Int, photo: PhotoReplaceRequestModel): Single<PhotoModel>
    fun removePhoto(id: Int): Single<Any>
}