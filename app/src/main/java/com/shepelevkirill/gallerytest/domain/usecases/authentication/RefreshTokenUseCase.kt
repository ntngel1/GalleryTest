package com.shepelevkirill.gallerytest.domain.usecases.authentication

import com.shepelevkirill.gallerytest.domain.gateway.AuthenticationGateway
import com.shepelevkirill.gallerytest.domain.models.RefreshTokenRequestModel
import com.shepelevkirill.gallerytest.domain.models.SessionModel
import com.shepelevkirill.gallerytest.domain.usecases.base.CompletableUseCase
import io.reactivex.Completable
import io.reactivex.Scheduler
import javax.inject.Inject

class RefreshTokenUseCase : CompletableUseCase<SessionModel> {
    private val authGateway: AuthenticationGateway

    @Inject
    constructor(authGateway: AuthenticationGateway) : super() {
        this.authGateway = authGateway
    }

    override fun build(param: SessionModel): Completable = Completable.create { emitter ->
        val refreshTokenRequestModel = RefreshTokenRequestModel(
            param.token,
            param.client
        )
        authGateway.getTokenByRefreshToken(refreshTokenRequestModel)
            .subscribe({ tokenModel ->
                param.token = tokenModel
                emitter.onComplete()
            }, {
                emitter.onError(it)
            })
    }
}