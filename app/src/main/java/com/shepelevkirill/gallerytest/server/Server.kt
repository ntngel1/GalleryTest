package com.shepelevkirill.gallerytest.server

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/*

    Server class to create an implementation of API

 */

class Server private constructor() {
    private val BASE_URL = "http://gallery.dev.webant.ru"
    private val retrofit: Retrofit

    init {
        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getApi(): GalleryService {
        return retrofit.create(GalleryService::class.java)
    }

    companion object {
        val instance: Server = Server()
    }
}