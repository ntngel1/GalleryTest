package com.shepelevkirill.gallerytest.domain.models

data class UserModel(
    val id: Int,
    val email: Int,
    val enabled: Boolean,
    val phone: String,
    val fullName: String,
    val username: String,
    val roles: List<String>
)