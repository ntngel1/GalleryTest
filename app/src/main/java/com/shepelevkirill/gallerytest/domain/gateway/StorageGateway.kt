package com.shepelevkirill.gallerytest.domain.gateway

import io.reactivex.Single
import java.io.File
import java.net.URI

interface StorageGateway {

    fun getPhotoFile(uri: URI): Single<File>
    fun getPhotoFile(uri: String): Single<File>
}