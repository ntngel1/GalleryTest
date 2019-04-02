package com.shepelevkirill.gallerytest.domain.usecases.authentication

import com.shepelevkirill.gallerytest.domain.gateway.AuthenticationGateway
import com.shepelevkirill.gallerytest.domain.gateway.ClientGateway
import com.shepelevkirill.gallerytest.domain.models.*
import com.shepelevkirill.gallerytest.domain.usecases.base.SingleUseCase
import io.reactivex.Single
import java.util.*
import javax.inject.Inject

class SignInUseCase : SingleUseCase<SignInUseCase.Params, SessionModel> {
    data class Params(val username: String, val password: String)

    private val clientGateway: ClientGateway
    private val authenticationGateway: AuthenticationGateway

    @Inject
    constructor(clientGateway: ClientGateway, authGateway: AuthenticationGateway) : super() {
        this.clientGateway = clientGateway
        this.authenticationGateway = authGateway
    }

    override fun build(param: Params): Single<SessionModel> = Single.create { emitter ->
        val clientRequestModel = ClientCreateRequestModel(
            UUID.randomUUID().toString(),
            listOf("refresh_token", "password")
        )

        var client: ClientModel? = null
        var token: TokenModel? = null

        clientGateway.createClient(clientRequestModel)
            .flatMap { clientModel ->
                client = clientModel
                val request = TokenGetByUserRequestModel(
                    param.username, param.password, clientModel
                )
                authenticationGateway.getTokenByUser(request)
            }
            .flatMap { tokenModel ->
                token = tokenModel

                val session = SessionModel(
                    client!!, token!!
                )
                Single.just(session)
            }
            .subscribe({ session ->
                authenticationGateway.session = session
                emitter.onSuccess(session)
            }, {
                emitter.onError(it)
            })
    }
}