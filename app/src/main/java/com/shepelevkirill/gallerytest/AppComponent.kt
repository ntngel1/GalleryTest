package com.shepelevkirill.gallerytest

import com.shepelevkirill.gallerytest.data.app.AppModule
import com.shepelevkirill.gallerytest.data.network.GatewayModule
import com.shepelevkirill.gallerytest.data.network.RetrofitModule
import com.shepelevkirill.gallerytest.ui.adapters.PhotosAdapter
import com.shepelevkirill.gallerytest.ui.scenes.main.MainPresenter
import com.shepelevkirill.gallerytest.ui.scenes.photo.PhotoPresenter
import com.shepelevkirill.gallerytest.ui.scenes.photos.PhotosPresenter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(GatewayModule::class)])
interface AppComponent {
    fun inject(target: MainPresenter)

    fun inject(target: PhotosAdapter)
    fun inject(target: PhotosPresenter)

    fun inject(target: PhotoPresenter)


    @Component.Builder
    interface Builder {
        fun build(): AppComponent
        fun appModule(app: AppModule): Builder
        fun retrofitModule(retrofit: RetrofitModule): Builder
        fun gatewayModule(gateway: GatewayModule): Builder
    }
}