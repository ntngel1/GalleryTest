package com.shepelevkirill.gateway.network

import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory


class retrofit {
    companion object {
    fun getRetrofit(): Api {
        return Retrofit.Builder()
            .baseUrl("http://gallery.dev.webant.ru")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Api::class.java)
    }
    }

}