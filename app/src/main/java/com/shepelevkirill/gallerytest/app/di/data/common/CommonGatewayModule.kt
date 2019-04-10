package com.shepelevkirill.gallerytest.app.di.data.common

import android.content.Context
import com.shepelevkirill.common.gateway.CommonNetworkGateway
import com.shepelevkirill.gallerytest.domain.gateway.NetworkGateway
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object CommonGatewayModule {

    @Provides
    @Singleton
    fun provideNetworkGateway(context: Context): NetworkGateway = CommonNetworkGateway(context)

}