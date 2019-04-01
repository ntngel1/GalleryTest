package com.shepelevkirill.gallerytest.domain.gateway

interface NetworkGateway {
    fun isNetworkAvailable(): Boolean
}