package com.shepelevkirill.gallerytest.domain.gateway

import com.shepelevkirill.gallerytest.domain.models.UserCreateRequestModel
import com.shepelevkirill.gallerytest.domain.models.UserModel
import com.shepelevkirill.gallerytest.domain.models.UserUpdateRequestModel
import io.reactivex.Observable
import io.reactivex.Single

interface UserGateway {
    fun getUsers(page: Int, limit: Int): Observable<UserModel>
    fun getCurrentUsers(page: Int, limit: Int): Observable<UserModel>
    fun createUser(user: UserCreateRequestModel): Single<UserModel>
    fun getCurrentUser(): Single<UserModel>
    fun getUser(id: Int): Single<UserModel>
    fun updateUser(id: Int, user: UserUpdateRequestModel): Single<UserModel>
    fun removeUser(id: Int): Single<Any>
}