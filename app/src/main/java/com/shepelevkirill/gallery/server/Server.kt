package com.shepelevkirill.gallery.server

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit


/*

    Server class to create an implementation of API

 */

class Server private constructor() {
    private val BASE_URL = "http://gallery.dev.webant.ru"
    private val retrofit: Retrofit

    init {
        val okHttpClient = OkHttpClient().newBuilder()
            .connectTimeout(5, TimeUnit.SECONDS)
            .readTimeout(5, TimeUnit.SECONDS)
            .writeTimeout(5, TimeUnit.SECONDS)
            .build()

        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
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