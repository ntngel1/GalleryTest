package com.shepelevkirill.core.models

data class PhotosModel(
    val totalItems: Int,
    val itemsPerPage: Int,
    val countOfPages: Int,
    val data: List<PhotoModel>
)

data class PhotoModel(
    val id: Int,
    val name: String,
    val description: String,
    val new: Boolean,
    val popular: Boolean,
    val image: MediaObjectModel
)

data class PhotoCreateRequestModel(
    val name: String,
    val description: String,
    val new: Boolean,
    val popular: Boolean,
    val image: MediaObjectModel
)

// TODO Может быть можно обощить два интерфейса ниже в один???
data class PhotoUpdateRequestModel(
    val name: String,
    val description: String,
    val new: Boolean,
    val popular: Boolean,
    val image: MediaObjectModel
)

data class PhotoReplaceRequestModel(
    val name: String,
    val description: String,
    val new: Boolean,
    val popular: Boolean,
    val image: MediaObjectModel
)