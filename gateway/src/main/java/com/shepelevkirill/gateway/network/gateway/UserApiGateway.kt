package com.shepelevkirill.gateway.network.gateway

import com.shepelevkirill.core.gateway.UserGateway
import com.shepelevkirill.core.models.UserCreateRequestModel
import com.shepelevkirill.core.models.UserModel
import com.shepelevkirill.core.models.UserUpdateRequestModel
import com.shepelevkirill.gateway.network.Api
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