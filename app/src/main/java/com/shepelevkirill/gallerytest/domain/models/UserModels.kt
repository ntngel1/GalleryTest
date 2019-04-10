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

data class UserCreateRequestModel(
    val email: Int,
    val phone: String,
    val fullName: String,
    val password: String,
    val username: String,
    val roles: List<String>
)

data class UserUpdateRequestModel(
    val email: Int,
    val phone: String,
    val fullName: String,
    val oldPassword: String,
    val newPassword: String,
    val username: String,
    val roles: List<String>,
    val code: String
)