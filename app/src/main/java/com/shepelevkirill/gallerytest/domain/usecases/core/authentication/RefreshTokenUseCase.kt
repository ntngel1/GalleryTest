package com.shepelevkirill.gallerytest.domain.usecases.core.authentication

import com.shepelevkirill.gallerytest.domain.models.SessionModel
import io.reactivex.Completable

interface RefreshTokenUseCase {

    fun refreshToken(session: SessionModel): Completable
}