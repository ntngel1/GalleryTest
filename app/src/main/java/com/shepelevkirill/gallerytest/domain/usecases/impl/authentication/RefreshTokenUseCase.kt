package com.shepelevkirill.gallerytest.domain.usecases.impl.authentication

import com.shepelevkirill.gallerytest.domain.gateway.AuthenticationGateway
import com.shepelevkirill.gallerytest.domain.models.RefreshTokenRequestModel
import com.shepelevkirill.gallerytest.domain.models.SessionModel
import com.shepelevkirill.gallerytest.domain.usecases.core.authentication.RefreshTokenUseCase
import io.reactivex.Completable

class RefreshTokenUseCase (private val authGateway: AuthenticationGateway) : RefreshTokenUseCase {

    override fun refreshToken(session: SessionModel): Completable = Completable.defer {
        val requestModel = RefreshTokenRequestModel(session.token, session.client)

        authGateway.getTokenByRefreshToken(requestModel)
            .flatMapCompletable { Completable.complete() }
            .onErrorResumeNext { Completable.error(it) }
    }
}