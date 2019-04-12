package com.shepelevkirill.gallerytest.app.di.data.retrofit.interceptors

import com.shepelevkirill.gallerytest.app.App
import com.shepelevkirill.gallerytest.domain.gateway.AuthenticationGateway
import com.shepelevkirill.gallerytest.domain.models.SessionModel
import com.shepelevkirill.gallerytest.domain.models.TokenModel
import com.shepelevkirill.gallerytest.domain.usecases.core.authentication.RefreshTokenUseCase
import io.reactivex.schedulers.Schedulers
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

class Authentication : Interceptor {

    @Inject
    lateinit var refreshTokenUseCase: RefreshTokenUseCase
    @Inject
    lateinit var authGateway: AuthenticationGateway


    override fun intercept(chain: Interceptor.Chain): Response {
        App.appComponent.inject(this)

        val session = authGateway.session
        val request = chain.request()
        val requestBuilder = request.newBuilder()

        val isSignedIn = authGateway.isSignedIn()

        if (!isSignedIn) {
            return chain.proceed(request)
        }

        val response = sendRequest(chain, requestBuilder, session!!)
        if (!isAuthorizationError(response.code())) {
            return response
        } else {
            return sendRequestWithTokenRefresh(chain, requestBuilder, session)
        }
    }

    private fun setAuthHeader(requestBuilder: Request.Builder, token: TokenModel) {
        requestBuilder.addHeader("Authorization", "Bearer ${token.accessToken}")
    }

    private fun isAuthorizationError(resultCode: Int): Boolean {
        return resultCode == 401 || resultCode == 403
    }

    private fun refreshToken(session: SessionModel): Throwable? {
        return refreshTokenUseCase.refreshToken(session)
            .retry(3)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .blockingGet()
    }

    private fun sendRequest(chain: Interceptor.Chain, requestBuilder: Request.Builder, session: SessionModel): Response {
        setAuthHeader(requestBuilder, session.token)
        val request = requestBuilder.build()
        return chain.proceed(request)
    }

    private fun sendRequestWithTokenRefresh(chain: Interceptor.Chain, requestBuilder: Request.Builder, session: SessionModel) : Response {
        synchronized(this) {
            if (refreshToken(session) == null) {
                authGateway.invalidateSession()
            } else {
                setAuthHeader(requestBuilder, session.token)
            }

            val request = requestBuilder.build()
            return chain.proceed(request)
        }
    }
}