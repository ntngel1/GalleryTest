package com.shepelevkirill.gateway.network.gateway

import com.shepelevkirill.core.gateway.AuthorizationGateway
import com.shepelevkirill.core.models.TokenGetByRefreshTokenRequestModel
import com.shepelevkirill.core.models.TokenGetByUserRequestModel
import com.shepelevkirill.gateway.network.Api

class AuthorizationApiGateway(private val api: Api): AuthorizationGateway {
    override fun getToken(authData: TokenGetByUserRequestModel) = api.getTokenByUser(authData.clientId, authData.clientSecret, authData.username, authData.password)
    override fun getToken(authData: TokenGetByRefreshTokenRequestModel) = api.getTokenByRefreshToken(authData.clientId, authData.clientSecret, authData.refreshToken)
}