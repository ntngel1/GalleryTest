package com.shepelevkirill.gallerytest.domain.gateway

import com.shepelevkirill.gallerytest.domain.models.RefreshTokenRequestModel
import com.shepelevkirill.gallerytest.domain.models.SessionModel
import com.shepelevkirill.gallerytest.domain.models.TokenGetByUserRequestModel
import com.shepelevkirill.gallerytest.domain.models.TokenModel
import io.reactivex.Single

interface AuthenticationGateway {

    var session: SessionModel?

    fun isSignedIn(): Boolean
    fun invalidateSession()
    fun getTokenByUser(data: TokenGetByUserRequestModel): Single<TokenModel>
    fun getTokenByRefreshToken(data: RefreshTokenRequestModel): Single<TokenModel>
}