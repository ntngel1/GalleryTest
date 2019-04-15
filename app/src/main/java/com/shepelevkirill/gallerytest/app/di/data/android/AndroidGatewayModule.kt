package com.shepelevkirill.gallerytest.app.di.data.android

import android.content.Context
import com.shepelevkirill.common.gateway.AndroidNetworkGateway
import com.shepelevkirill.gallerytest.app.di.data.app.AppModule
import com.shepelevkirill.gallerytest.data.android.gateway.AndroidStorageGateway
import com.shepelevkirill.gallerytest.domain.gateway.NetworkGateway
import com.shepelevkirill.gallerytest.domain.gateway.StorageGateway
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [AppModule::class])
object AndroidGatewayModule {

    @Provides
    @Singleton
    fun provideNetworkGateway(context: Context): NetworkGateway = AndroidNetworkGateway(context)

    @Provides
    @Singleton
    fun provideStorageGateway(context: Context): StorageGateway = AndroidStorageGateway(context)
}