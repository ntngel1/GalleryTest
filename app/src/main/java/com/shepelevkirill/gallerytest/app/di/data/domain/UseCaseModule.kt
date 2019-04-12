package com.shepelevkirill.gallerytest.app.di.data.domain

import com.shepelevkirill.gallerytest.app.di.data.retrofit.RetrofitGatewayModule
import com.shepelevkirill.gallerytest.domain.gateway.AuthenticationGateway
import com.shepelevkirill.gallerytest.domain.gateway.ClientGateway
import com.shepelevkirill.gallerytest.domain.gateway.MediaObjectGateway
import com.shepelevkirill.gallerytest.domain.gateway.PhotoGateway
import com.shepelevkirill.gallerytest.domain.usecases.core.authentication.RefreshTokenUseCase
import com.shepelevkirill.gallerytest.domain.usecases.core.authentication.SignInUseCase
import com.shepelevkirill.gallerytest.domain.usecases.core.photos.UploadPhotoUseCase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [RetrofitGatewayModule::class])
class UseCaseModule {

    @Provides
    @Singleton
    fun provideSignInUseCase(authGateway: AuthenticationGateway, clientGateway: ClientGateway): SignInUseCase =
        com.shepelevkirill.gallerytest.domain.usecases.impl.authentication.SignInUseCase(clientGateway, authGateway) // TODO Norm ili ne norm?

    @Provides
    @Singleton
    fun provideRefreshTokenUseCase(authGateway: AuthenticationGateway): RefreshTokenUseCase =
            com.shepelevkirill.gallerytest.domain.usecases.impl.authentication.RefreshTokenUseCase(authGateway)

    @Provides
    @Singleton
    fun provideUploadPhotoUseCase(photoGateway: PhotoGateway, mediaObjectGateway: MediaObjectGateway): UploadPhotoUseCase =
            com.shepelevkirill.gallerytest.domain.usecases.impl.photos.UploadPhotoUseCase(photoGateway, mediaObjectGateway)
}