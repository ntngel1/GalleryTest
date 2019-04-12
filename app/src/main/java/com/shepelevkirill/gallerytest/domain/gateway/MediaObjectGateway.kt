package com.shepelevkirill.gallerytest.domain.gateway

import com.shepelevkirill.gallerytest.domain.models.MediaObjectModel
import io.reactivex.Single
import java.io.File

interface MediaObjectGateway {

    fun uploadPhoto(file: File): Single<MediaObjectModel>
    fun getMediaObjectPathById(id: Int): String
}