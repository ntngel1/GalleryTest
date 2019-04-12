package com.shepelevkirill.gallerytest.data.server.gateway

import com.shepelevkirill.gallerytest.domain.gateway.ClientGateway
import com.shepelevkirill.gallerytest.domain.models.ClientCreateRequestModel
import com.shepelevkirill.gallerytest.domain.models.ClientModel
import com.shepelevkirill.server.Api
import io.reactivex.Single

class ClientApiGateway(private val api: Api) : ClientGateway {

    override fun createClient(client: ClientCreateRequestModel): Single<ClientModel> = api.createClient(client)
}