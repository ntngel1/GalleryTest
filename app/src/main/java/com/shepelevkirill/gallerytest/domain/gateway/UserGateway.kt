package com.shepelevkirill.gallerytest.domain.gateway

import com.shepelevkirill.gallerytest.domain.models.UserModel
import io.reactivex.Single

interface UserGateway {

    fun getCurrentUser(): Single<UserModel>
}