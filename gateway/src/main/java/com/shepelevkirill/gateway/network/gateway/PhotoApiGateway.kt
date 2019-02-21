package com.shepelevkirill.gateway.network.gateway

import com.shepelevkirill.core.gateway.PhotoGateway
import com.shepelevkirill.core.models.PhotoCreateRequestModel
import com.shepelevkirill.core.models.PhotoModel
import com.shepelevkirill.core.models.PhotoReplaceRequestModel
import com.shepelevkirill.core.models.PhotoUpdateRequestModel
import com.shepelevkirill.gateway.network.Api
import io.reactivex.Observable
import io.reactivex.Single

class PhotoApiGateway(private var api: Api) : PhotoGateway {
    override fun getPhotos(page: Int, limit: Int, new: Boolean, popular: Boolean): Observable<PhotoModel> =
        api.getPhotos(page, limit, new, popular)
            .flatMapObservable { Observable.fromIterable(it) }


    override fun createPhoto(photo: PhotoCreateRequestModel): Single<PhotoModel> =
        api.createPhoto(photo)

    override fun getPhoto(id: Int): Single<PhotoModel> =
        api.getPhoto(id)

    override fun updatePhoto(id: Int, photo: PhotoUpdateRequestModel): Single<PhotoModel> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun replacePhoto(id: Int, photo: PhotoReplaceRequestModel): Single<PhotoModel> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun removePhoto(id: Int): Single<Any> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}