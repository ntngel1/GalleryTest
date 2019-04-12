package com.shepelevkirill.gallerytest.domain.models

data class ClientModel(
    val id: Int,
    val name: String,
    val randomId: String,
    val secret: String,
    val allowedGrantTypes: List<String>
)

data class ClientCreateRequestModel(
    val name: String,
    val allowedGrantTypes: List<String>
)