package com.shepelevkirill.gallerytest.app.di

import com.shepelevkirill.gallerytest.app.di.data.android.AndroidGatewayModule
import com.shepelevkirill.gallerytest.app.di.data.app.AppModule
import com.shepelevkirill.gallerytest.app.di.data.domain.UseCaseModule
import com.shepelevkirill.gallerytest.app.di.data.retrofit.RetrofitGatewayModule
import com.shepelevkirill.gallerytest.app.di.data.retrofit.RetrofitModule
import com.shepelevkirill.gallerytest.app.di.data.retrofit.interceptors.Authentication
import com.shepelevkirill.gallerytest.app.ui.adapters.PhotosAdapter
import com.shepelevkirill.gallerytest.app.ui.scenes.authentication.AuthenticationPresenter
import com.shepelevkirill.gallerytest.app.ui.scenes.main.MainPresenter
import com.shepelevkirill.gallerytest.app.ui.scenes.photo.PhotoPresenter
import com.shepelevkirill.gallerytest.app.ui.scenes.photos.PhotosPresenter
import com.shepelevkirill.gallerytest.app.ui.scenes.upload.UploadPresenter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [RetrofitGatewayModule::class, AndroidGatewayModule::class, UseCaseModule::class])
interface AppComponent {
    // TODO REFACTOR THIS
    fun inject(target: MainPresenter)
    fun inject(target: PhotosAdapter)
    fun inject(target: PhotosPresenter)
    fun inject(target: PhotoPresenter)
    fun inject(target: UploadPresenter)
    fun inject(target: AuthenticationPresenter)
    fun inject(target: Authentication)

    fun provideMainPresenter(): MainPresenter
    fun providePhotoPresenter(): PhotoPresenter
    fun provideUploadPresenter(): UploadPresenter
    fun provideAuthenticationPresenter(): AuthenticationPresenter

    @Component.Builder
    interface Builder {
        fun build(): AppComponent
        fun appModule(app: AppModule): Builder
        fun retrofitModule(retrofit: RetrofitModule): Builder
        fun retrofitGatewayModule(gateway: RetrofitGatewayModule): Builder
        fun androidGatewayModule(gateway: AndroidGatewayModule): Builder
        fun useCaseModule(useCaseModule: UseCaseModule): Builder
    }
}