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

// TODO Может быть можно обощить два интерфейса ниже в один???
data class ClientUpdateRequestModel(
    val name: String,
    val allowedGrantTypes: List<String>
)

data class ClientReplaceRequestModel(
    val name: String,
    val allowedGrantTypes: List<String>
)