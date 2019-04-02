package com.shepelevkirill.gallerytest.app.di.data.server

import com.shepelevkirill.gallerytest.app.di.data.server.interceptors.Authentication
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(includes = [(ServerGatewayModule::class)])
object RetrofitModule {

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient,
                        callAdapterFactory: CallAdapter.Factory,
                        converterFactory: Converter.Factory): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://gallery.dev.webant.ru")
            .client(client)
            .addCallAdapterFactory(callAdapterFactory)
            .addConverterFactory(converterFactory)
            .build()
    }

    @Provides
    @Singleton
    fun provideClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(Authentication())
            .build()

        return client
    }

    @Provides
    @Singleton
    fun provideCallAdapterFactory(): CallAdapter.Factory = RxJava2CallAdapterFactory.create()

    @Provides
    @Singleton
    fun provideConverterFactory(): Converter.Factory = GsonConverterFactory.create()
}