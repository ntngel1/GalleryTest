package com.shepelevkirill.gallerytest.domain.models

data class MediaObjectModel(
    val id: Int,
    val contentUrl: String
)

data class MediaObjectCreateRequestModel(
    val id: Int,
    val file: String,
    val contentUrl: String
)

// TODO Может быть можно обощить два интерфейса ниже в один???
data class MediaObjectUpdateRequestModel(
    val id: Int,
    val contentUrl: String
)

data class MediaObjectReplaceRequestModel(
    val id: Int,
    val contentUrl: String
)