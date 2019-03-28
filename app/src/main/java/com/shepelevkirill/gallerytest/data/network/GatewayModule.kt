package com.shepelevkirill.gallerytest.data.network

import android.content.Context
import com.shepelevkirill.core.gateway.*
import com.shepelevkirill.core.gateway.NetworkGateway
import com.shepelevkirill.gallerytest.data.app.AppModule
import com.shepelevkirill.gateway.network.Api
import com.shepelevkirill.gateway.network.gateway.*
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(includes = [(RetrofitModule::class), (AppModule::class)])
object GatewayModule {

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): Api = retrofit.create(Api::class.java)

    @Provides
    @Singleton
    fun provideClientGateway(api: Api): ClientGateway = ClientApiGateway(api)

    @Provides
    @Singleton
    fun providePhotoGateway(api: Api): PhotoGateway = PhotoApiGateway(api)

    @Provides
    @Singleton
    fun provideMediaObjectGateway(api: Api): MediaObjectGateway = MediaObjectApiGateway(api)

    @Provides
    @Singleton
    fun provideUserGateway(api: Api): UserGateway = UserApiGateway(api)

    @Provides
    @Singleton
    fun provideNetworkGateway(context: Context): NetworkGateway = com.shepelevkirill.gateway.network.gateway.NetworkGateway(context)
}