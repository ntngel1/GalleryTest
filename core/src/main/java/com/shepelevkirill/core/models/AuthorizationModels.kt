package com.shepelevkirill.core.models

data class TokenGetByUserRequestModel(
    val clientId: String,
    val clientSecret: String,
    val username: String,
    val password: String,
    val grantType: String = "password"
)

data class TokenGetByRefreshTokenRequestModel(
    val clientId: String,
    val clientSecret: String,
    val refreshToken: String,
    val grantType: String = "refresh_token"
)