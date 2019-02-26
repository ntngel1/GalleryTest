package com.shepelevkirill.gallerytest.data.network

import com.shepelevkirill.core.gateway.ClientGateway
import com.shepelevkirill.core.gateway.MediaObjectGateway
import com.shepelevkirill.core.gateway.PhotoGateway
import com.shepelevkirill.core.gateway.UserGateway
import com.shepelevkirill.gateway.network.Api
import com.shepelevkirill.gateway.network.gateway.ClientApiGateway
import com.shepelevkirill.gateway.network.gateway.MediaObjectApiGateway
import com.shepelevkirill.gateway.network.gateway.PhotoApiGateway
import com.shepelevkirill.gateway.network.gateway.UserApiGateway
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(includes = [(RetrofitModule::class)])
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
}