package com.shepelevkirill.gateway.network.gateway

import com.shepelevkirill.core.gateway.MediaObjectGateway
import com.shepelevkirill.core.models.MediaObjectCreateRequestModel
import com.shepelevkirill.core.models.MediaObjectModel
import com.shepelevkirill.core.models.MediaObjectReplaceRequestModel
import com.shepelevkirill.core.models.MediaObjectUpdateRequestModel
import io.reactivex.Observable
import io.reactivex.Single

class MediaObjectApiGateway : MediaObjectGateway {
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