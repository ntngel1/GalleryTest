package com.shepelevkirill.core.gateway

import com.shepelevkirill.core.models.ClientCreateRequestModel
import com.shepelevkirill.core.models.ClientModel
import com.shepelevkirill.core.models.ClientReplaceRequestModel
import com.shepelevkirill.core.models.ClientUpdateRequestModel
import io.reactivex.Observable
import io.reactivex.Single

interface ClientGateway {
    fun getClients(page: Int, limit: Int): Observable<ClientModel>

    fun createClient(client: ClientCreateRequestModel): Single<ClientModel>
    fun getClient(id: Int): Single<ClientModel>
    fun updateClient(id: Int, client: ClientUpdateRequestModel): Single<ClientModel>
    fun replaceClient(id: Int, client: ClientReplaceRequestModel): Single<ClientModel>
    fun removeClient(id: Int): Single<Any> // TODO ANY????
}