package com.shepelevkirill.gallerytest.app.di.data.retrofit

import com.shepelevkirill.gallerytest.app.di.data.app.AppModule
import com.shepelevkirill.gallerytest.data.server.gateway.AuthenticationApiGateway
import com.shepelevkirill.gallerytest.domain.gateway.*
import com.shepelevkirill.server.Api
import com.shepelevkirill.gallerytest.data.server.gateway.ClientApiGateway
import com.shepelevkirill.gallerytest.data.server.gateway.MediaObjectApiGateway
import com.shepelevkirill.server.gateway.PhotoApiGateway
import com.shepelevkirill.gallerytest.data.server.gateway.UserApiGateway
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(includes = [(RetrofitModule::class), (AppModule::class)])
object RetrofitGatewayModule {

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
    fun provideAuthenticationGateway(api: Api): AuthenticationGateway = AuthenticationApiGateway(api)
}