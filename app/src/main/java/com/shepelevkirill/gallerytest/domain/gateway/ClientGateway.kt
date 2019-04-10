package com.shepelevkirill.gallerytest.domain.gateway

import com.shepelevkirill.gallerytest.domain.models.ClientCreateRequestModel
import com.shepelevkirill.gallerytest.domain.models.ClientModel
import com.shepelevkirill.gallerytest.domain.models.ClientReplaceRequestModel
import com.shepelevkirill.gallerytest.domain.models.ClientUpdateRequestModel
import io.reactivex.Observable
import io.reactivex.Single

interface ClientGateway {
    fun getClients(page: Int, limit: Int): Observable<ClientModel>
    fun createClient(client: ClientCreateRequestModel): Single<ClientModel>
    fun getClient(id: Int): Single<ClientModel>
    fun updateClient(id: Int, client: ClientUpdateRequestModel): Single<ClientModel>
    fun replaceClient(id: Int, client: ClientReplaceRequestModel): Single<ClientModel>
    fun removeClient(id: Int): Single<Any>
}