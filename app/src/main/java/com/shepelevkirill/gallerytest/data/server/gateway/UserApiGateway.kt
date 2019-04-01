package com.shepelevkirill.server.gateway

import com.shepelevkirill.gallerytest.domain.gateway.UserGateway
import com.shepelevkirill.gallerytest.domain.models.UserCreateRequestModel
import com.shepelevkirill.gallerytest.domain.models.UserModel
import com.shepelevkirill.gallerytest.domain.models.UserUpdateRequestModel
import com.shepelevkirill.server.Api
import io.reactivex.Observable
import io.reactivex.Single

class UserApiGateway(private val api: Api) : UserGateway {
    override fun getUsers(page: Int, limit: Int): Observable<UserModel> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getCurrentUsers(page: Int, limit: Int): Observable<UserModel> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun createUser(user: UserCreateRequestModel): Single<UserModel> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getCurrentUser(): Single<UserModel> = api.getCurrentUser()

    override fun getUser(id: Int): Single<UserModel> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun updateUser(id: Int, user: UserUpdateRequestModel): Single<UserModel> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun removeUser(id: Int): Single<Any> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}