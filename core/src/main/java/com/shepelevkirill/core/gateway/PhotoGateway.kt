package com.shepelevkirill.core.gateway

import com.shepelevkirill.core.models.PhotoCreateRequestModel
import io.reactivex.Single
import com.shepelevkirill.core.models.PhotoModel
import com.shepelevkirill.core.models.PhotoReplaceRequestModel
import com.shepelevkirill.core.models.PhotoUpdateRequestModel

interface PhotoGateway {
    fun getPhotos(page: Int, limit: Int, new: Boolean, popular: Boolean): Single<List<PhotoModel>>

    fun createPhoto(photo: PhotoCreateRequestModel): Single<PhotoModel>
    fun getPhoto(id: Int): Single<PhotoModel>
    fun updatePhoto(id: Int, photo: PhotoUpdateRequestModel): Single<PhotoModel>
    fun replacePhoto(id: Int, photo: PhotoReplaceRequestModel): Single<PhotoModel>
    fun removePhoto(id: Int): Single<Any> // TODO WHAT TO USE? SINGLE<STRING> OR SINGLE<ANY>
}