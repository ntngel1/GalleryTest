package com.shepelevkirill.gallerytest.domain.usecases.core.authentication

import com.shepelevkirill.gallerytest.domain.models.SessionModel
import io.reactivex.Single

interface SignInUseCase {

    fun signIn(username: String, password: String): Single<SessionModel>
}