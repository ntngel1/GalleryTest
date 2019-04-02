package com.shepelevkirill.gallerytest.app.di

import com.shepelevkirill.gallerytest.app.di.data.app.AppModule
import com.shepelevkirill.gallerytest.app.di.data.common.CommonGatewayModule
import com.shepelevkirill.gallerytest.app.di.data.server.ServerGatewayModule
import com.shepelevkirill.gallerytest.app.di.data.server.RetrofitModule
import com.shepelevkirill.gallerytest.app.di.data.server.interceptors.Authentication
import com.shepelevkirill.gallerytest.app.ui.adapters.PhotosAdapter
import com.shepelevkirill.gallerytest.ui.scenes.authentication.AuthenticationPresenter
import com.shepelevkirill.gallerytest.app.ui.scenes.main.MainPresenter
import com.shepelevkirill.gallerytest.app.ui.scenes.photo.PhotoPresenter
import com.shepelevkirill.gallerytest.app.ui.scenes.photos.PhotosPresenter
import com.shepelevkirill.gallerytest.app.ui.scenes.upload.UploadPresenter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(ServerGatewayModule::class), (CommonGatewayModule::class)])
interface AppComponent {
    fun inject(target: MainPresenter)
    fun inject(target: PhotosAdapter)
    fun inject(target: PhotosPresenter)
    fun inject(target: PhotoPresenter)
    fun inject(target: UploadPresenter)
    fun inject(target: AuthenticationPresenter)

    fun inject(target: Authentication)

    @Component.Builder
    interface Builder {
        fun build(): AppComponent
        fun appModule(app: AppModule): Builder
        fun retrofitModule(retrofit: RetrofitModule): Builder

        fun serverGatewayModule(gateway: ServerGatewayModule): Builder
        fun commonGatewayModule(gateway: CommonGatewayModule): Builder
    }
}