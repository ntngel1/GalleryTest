package com.shepelevkirill.gallerytest.data.server.gateway

import com.shepelevkirill.gallerytest.domain.gateway.MediaObjectGateway
import com.shepelevkirill.gallerytest.domain.models.MediaObjectModel
import com.shepelevkirill.server.Api
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
}