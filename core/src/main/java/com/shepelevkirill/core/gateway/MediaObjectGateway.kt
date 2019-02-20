package com.shepelevkirill.core.gateway

import com.shepelevkirill.core.models.MediaObjectCreateRequestModel
import com.shepelevkirill.core.models.MediaObjectModel
import com.shepelevkirill.core.models.MediaObjectReplaceRequestModel
import com.shepelevkirill.core.models.MediaObjectUpdateRequestModel
import io.reactivex.Single

interface MediaObjectGateway {
    fun getMediaObjects(page: Int, limit: Int): Single<List<MediaObjectModel>>

    fun createMediaObject(mediaObject: MediaObjectCreateRequestModel): Single<MediaObjectModel>
    fun getMediaObject(id: Int): Single<MediaObjectModel>
    fun updateMediaObject(id: Int, mediaObject: MediaObjectUpdateRequestModel): Single<MediaObjectModel>
    fun replaceMediaObject(id: Int, mediaObject: MediaObjectReplaceRequestModel): Single<MediaObjectModel>
}