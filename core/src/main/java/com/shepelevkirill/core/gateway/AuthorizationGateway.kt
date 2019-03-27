package com.shepelevkirill.core.gateway

import com.shepelevkirill.core.models.TokenGetByRefreshTokenRequestModel
import com.shepelevkirill.core.models.TokenGetByUserRequestModel

interface AuthorizationGateway {
    fun getToken(authData: TokenGetByUserRequestModel)
    fun getToken(authData: TokenGetByRefreshTokenRequestModel)
}