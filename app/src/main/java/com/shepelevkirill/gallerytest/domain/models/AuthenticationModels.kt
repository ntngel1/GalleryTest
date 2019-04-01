package com.shepelevkirill.gallerytest.domain.models

import com.google.gson.annotations.SerializedName

data class TokenModel(
    @SerializedName("access_token")
    val accessToken: String,
    @SerializedName("expires_in")
    val expiresIn: Int,
    @SerializedName("token_type")
    val tokenType: String,
    @SerializedName("refresh_token")
    val refreshToken: String
)

data class SessionModel(
    val client: ClientModel,
    var token: TokenModel
)

data class TokenGetByUserRequestModel(
    val username: String,
    val password: String,
    val client: ClientModel
)

data class RefreshTokenRequestModel(
    val token: TokenModel,
    val client: ClientModel
)