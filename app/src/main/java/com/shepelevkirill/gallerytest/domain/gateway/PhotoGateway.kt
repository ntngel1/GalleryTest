package com.shepelevkirill.gallerytest.domain.gateway

import com.shepelevkirill.gallerytest.domain.models.PhotoCreateRequestModel
import com.shepelevkirill.gallerytest.domain.models.PhotoModel
import com.shepelevkirill.gallerytest.domain.models.PhotosModel
import io.reactivex.Single

interface PhotoGateway {

    fun getPhotos(page: Int, limit: Int, new: Boolean?, popular: Boolean?): Single<PhotosModel>
    fun getPhotosData(new: Boolean, popular: Boolean): Single<PhotosModel>
    fun getPhotoUrl(image: String): String
    fun createPhoto(photo: PhotoCreateRequestModel): Single<PhotoModel>
    fun getPhoto(id: Int): Single<PhotoModel>
}