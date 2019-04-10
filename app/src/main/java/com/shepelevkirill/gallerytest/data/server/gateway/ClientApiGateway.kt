package com.shepelevkirill.server.gateway

import com.shepelevkirill.gallerytest.domain.gateway.ClientGateway
import com.shepelevkirill.gallerytest.domain.models.ClientCreateRequestModel
import com.shepelevkirill.gallerytest.domain.models.ClientModel
import com.shepelevkirill.gallerytest.domain.models.ClientReplaceRequestModel
import com.shepelevkirill.gallerytest.domain.models.ClientUpdateRequestModel
import com.shepelevkirill.server.Api
import io.reactivex.Observable
import io.reactivex.Single

class ClientApiGateway(private val api: Api) : ClientGateway {
    override fun getClients(page: Int, limit: Int): Observable<ClientModel> {
        TODO()
    }

    override fun createClient(client: ClientCreateRequestModel): Single<ClientModel> = api.createClient(client)
    override fun getClient(id: Int): Single<ClientModel> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun updateClient(id: Int, client: ClientUpdateRequestModel): Single<ClientModel> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun replaceClient(id: Int, client: ClientReplaceRequestModel): Single<ClientModel> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun removeClient(id: Int): Single<Any> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}