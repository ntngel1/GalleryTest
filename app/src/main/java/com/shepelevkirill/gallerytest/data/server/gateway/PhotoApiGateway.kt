package com.shepelevkirill.server.gateway

import com.shepelevkirill.gallerytest.BuildConfig
import com.shepelevkirill.gallerytest.domain.gateway.PhotoGateway
import com.shepelevkirill.gallerytest.domain.models.PhotoCreateRequestModel
import com.shepelevkirill.gallerytest.domain.models.PhotoModel
import com.shepelevkirill.gallerytest.domain.models.PhotosModel
import com.shepelevkirill.server.Api
import io.reactivex.Single

class PhotoApiGateway(private var api: Api) : PhotoGateway {

    override fun getPhotos(page: Int, limit: Int, new: Boolean?, popular: Boolean?): Single<PhotosModel> = api.getPhotos(page, limit, new, popular)

    override fun getPhotosData(new: Boolean, popular: Boolean): Single<PhotosModel> = getPhotos(1, 1, new, popular)

    override fun getPhotoUrl(image: String): String = "${BuildConfig.BASE_URL}/media/$image"

    override fun createPhoto(photo: PhotoCreateRequestModel): Single<PhotoModel> = api.createPhoto(photo)

    override fun getPhoto(id: Int): Single<PhotoModel> = api.getPhoto(id)
}