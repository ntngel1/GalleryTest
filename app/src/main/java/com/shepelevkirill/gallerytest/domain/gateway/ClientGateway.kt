package com.shepelevkirill.gallerytest.domain.gateway

import com.shepelevkirill.gallerytest.domain.models.ClientCreateRequestModel
import com.shepelevkirill.gallerytest.domain.models.ClientModel
import io.reactivex.Single

interface ClientGateway {

    fun createClient(client: ClientCreateRequestModel): Single<ClientModel>
}