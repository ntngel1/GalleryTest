package com.shepelevkirill.gallerytest.data.server.gateway

import com.shepelevkirill.gallerytest.domain.gateway.AuthenticationGateway
import com.shepelevkirill.gallerytest.domain.models.RefreshTokenRequestModel
import com.shepelevkirill.gallerytest.domain.models.TokenGetByUserRequestModel
import com.shepelevkirill.gallerytest.domain.models.TokenModel
import com.shepelevkirill.server.Api
import io.reactivex.Single

class AuthenticationApiGateway(private val api: Api) : AuthenticationGateway {
    override fun getTokenByUser(data: TokenGetByUserRequestModel): Single<TokenModel> {
        return api.getTokenByUser(
            data.username, data.password,
            "${data.client.id}_${data.client.randomId}",
            data.client.secret)
    }

    override fun getTokenByRefreshToken(data: RefreshTokenRequestModel): Single<TokenModel> {
        return api.getTokenByRefreshToken(
            data.token.refreshToken, "${data.client.id}_${data.client.randomId}",
            data.client.secret)
    }

}