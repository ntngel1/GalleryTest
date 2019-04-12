package com.shepelevkirill.gallerytest.domain.models

import java.io.Serializable

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
) : Serializable

data class PhotoCreateRequestModel(
    val name: String,
    val description: String,
    val new: Boolean,
    val popular: Boolean,
    val image: String
)