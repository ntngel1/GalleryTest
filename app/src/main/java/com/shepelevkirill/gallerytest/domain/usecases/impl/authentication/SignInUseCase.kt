package com.shepelevkirill.gallerytest.domain.usecases.impl.authentication

import com.shepelevkirill.gallerytest.domain.gateway.AuthenticationGateway
import com.shepelevkirill.gallerytest.domain.gateway.ClientGateway
import com.shepelevkirill.gallerytest.domain.models.*
import com.shepelevkirill.gallerytest.domain.usecases.core.authentication.SignInUseCase
import io.reactivex.Single
import java.util.*

class SignInUseCase (private val clientGateway: ClientGateway, private val authGateway: AuthenticationGateway) : SignInUseCase {

    override fun signIn(username: String, password: String): Single<SessionModel> = Single.defer {
        val clientRequestModel = ClientCreateRequestModel(UUID.randomUUID().toString(), listOf("refresh_token", "password"))

        lateinit var clientModel: ClientModel
        lateinit var tokenModel: TokenModel
        clientGateway.createClient(clientRequestModel)
            .flatMap { client ->
                clientModel = client
                val tokenRequestModel = TokenGetByUserRequestModel(username, password, client)
                authGateway.getTokenByUser(tokenRequestModel)
            }
            .flatMap { token ->
                tokenModel = token
                val session = SessionModel(clientModel, tokenModel)
                authGateway.session = session
                Single.just(session)
            }
    }
}