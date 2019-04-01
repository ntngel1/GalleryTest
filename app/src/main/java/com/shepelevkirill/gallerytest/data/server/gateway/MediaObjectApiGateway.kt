package com.shepelevkirill.server.gateway

import com.shepelevkirill.gallerytest.domain.gateway.MediaObjectGateway
import com.shepelevkirill.gallerytest.domain.models.MediaObjectCreateRequestModel
import com.shepelevkirill.gallerytest.domain.models.MediaObjectModel
import com.shepelevkirill.gallerytest.domain.models.MediaObjectReplaceRequestModel
import com.shepelevkirill.gallerytest.domain.models.MediaObjectUpdateRequestModel
import com.shepelevkirill.server.Api
import io.reactivex.Observable
import io.reactivex.Single
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class MediaObjectApiGateway(private val api: Api) : MediaObjectGateway {
    override fun uploadPhoto(file: File): Single<MediaObjectModel> {
        val requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file)
        val body = MultipartBody.Part.createFormData("file", file.name, requestFile)
        return api.uploadPhoto(body)
    }

    override fun getMediaObjectPathById(id: Int): String = "/api/media_objects/$id"

    override fun getMediaObjects(page: Int, limit: Int): Observable<MediaObjectModel> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun createMediaObject(mediaObject: MediaObjectCreateRequestModel): Single<MediaObjectModel> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getMediaObject(id: Int): Single<MediaObjectModel> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun updateMediaObject(id: Int, mediaObject: MediaObjectUpdateRequestModel): Single<MediaObjectModel> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun replaceMediaObject(id: Int, mediaObject: MediaObjectReplaceRequestModel): Single<MediaObjectModel> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}