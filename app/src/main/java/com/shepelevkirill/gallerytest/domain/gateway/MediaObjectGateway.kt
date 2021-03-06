package com.shepelevkirill.gallerytest.domain.gateway

import com.shepelevkirill.gallerytest.domain.models.MediaObjectCreateRequestModel
import com.shepelevkirill.gallerytest.domain.models.MediaObjectModel
import com.shepelevkirill.gallerytest.domain.models.MediaObjectReplaceRequestModel
import com.shepelevkirill.gallerytest.domain.models.MediaObjectUpdateRequestModel
import io.reactivex.Observable
import io.reactivex.Single
import java.io.File

interface MediaObjectGateway {
    fun uploadPhoto(file: File): Single<MediaObjectModel>
    fun getMediaObjectPathById(id: Int): String
    fun getMediaObjects(page: Int, limit: Int): Observable<MediaObjectModel>
    fun createMediaObject(mediaObject: MediaObjectCreateRequestModel): Single<MediaObjectModel>
    fun getMediaObject(id: Int): Single<MediaObjectModel>
    fun updateMediaObject(id: Int, mediaObject: MediaObjectUpdateRequestModel): Single<MediaObjectModel>
    fun replaceMediaObject(id: Int, mediaObject: MediaObjectReplaceRequestModel): Single<MediaObjectModel>
}