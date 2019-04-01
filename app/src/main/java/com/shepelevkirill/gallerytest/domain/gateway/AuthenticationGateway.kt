package com.shepelevkirill.gallerytest.domain.gateway

import com.shepelevkirill.gallerytest.domain.models.RefreshTokenRequestModel
import com.shepelevkirill.gallerytest.domain.models.TokenGetByUserRequestModel
import com.shepelevkirill.gallerytest.domain.models.TokenModel
import io.reactivex.Single

interface AuthenticationGateway {
    fun getTokenByUser(data: TokenGetByUserRequestModel): Single<TokenModel>
    fun getTokenByRefreshToken(data: RefreshTokenRequestModel): Single<TokenModel>
}