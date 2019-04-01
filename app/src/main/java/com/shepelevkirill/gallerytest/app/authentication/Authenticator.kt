package com.shepelevkirill.gallerytest.app.authentication

import android.util.Log
import com.shepelevkirill.gallerytest.app.App
import com.shepelevkirill.gallerytest.domain.gateway.AuthenticationGateway
import com.shepelevkirill.gallerytest.domain.usecases.authentication.RefreshTokenUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

class Authenticator : Authenticator {
    @Inject
    lateinit var authenticationGateway: AuthenticationGateway

    private val refreshTokenUseCase by lazy {
        RefreshTokenUseCase(authenticationGateway)
    }

    override fun authenticate(route: Route?, response: Response): Request? {
        App.appComponent.inject(this)
        val session = App.session
        if (session != null) {
            val error = refreshTokenUseCase.setSchedulers(Schedulers.io(), AndroidSchedulers.mainThread())
                .execute(session)
                .blockingGet()

            if (error == null) {
                return response.request()
                    .newBuilder()
                    .header("Authorization", "Bearer ${session.token.accessToken}")
                    .build()
            } else {
                return null
            }
        } else {
            return null
        }
    }

}