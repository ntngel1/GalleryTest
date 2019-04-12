package com.shepelevkirill.gallerytest.data.server.gateway

import com.shepelevkirill.gallerytest.domain.gateway.UserGateway
import com.shepelevkirill.gallerytest.domain.models.UserModel
import com.shepelevkirill.server.Api
import io.reactivex.Single

class UserApiGateway(private val api: Api) : UserGateway {

    override fun getCurrentUser(): Single<UserModel> = api.getCurrentUser()
}