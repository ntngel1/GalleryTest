package com.shepelevkirill.gateway.network.gateway

import com.shepelevkirill.core.gateway.ClientGateway
import com.shepelevkirill.core.models.ClientCreateRequestModel
import com.shepelevkirill.core.models.ClientModel
import com.shepelevkirill.core.models.ClientReplaceRequestModel
import com.shepelevkirill.core.models.ClientUpdateRequestModel
import io.reactivex.Observable
import io.reactivex.Single

class ClientApiGateway : ClientGateway {
    override fun getClients(page: Int, limit: Int): Observable<ClientModel> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun createClient(client: ClientCreateRequestModel): Single<ClientModel> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

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