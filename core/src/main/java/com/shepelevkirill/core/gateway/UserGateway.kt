package com.shepelevkirill.core.gateway

import com.shepelevkirill.core.models.UserCreateRequestModel
import com.shepelevkirill.core.models.UserModel
import com.shepelevkirill.core.models.UserUpdateRequestModel
import io.reactivex.Observable
import io.reactivex.Single

interface UserGateway {
    fun getUsers(page: Int, limit: Int): Observable<UserModel>
    fun getCurrentUsers(page: Int, limit: Int): Observable<UserModel>

    fun createUser(user: UserCreateRequestModel): Single<UserModel>
    fun getUser(id: Int): Single<UserModel>
    fun updateUser(id: Int, user: UserUpdateRequestModel): Single<UserModel>
    fun removeUser(id: Int): Single<Any> // TODO WHAT TO USE? SINGLE<STRING> OR SINGLE<ANY>
}