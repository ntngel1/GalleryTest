package com.shepelevkirill.gallerytest

import com.shepelevkirill.gallerytest.data.network.GatewayModule
import com.shepelevkirill.gallerytest.data.network.RetrofitModule
import com.shepelevkirill.gallerytest.ui.scenes.main.MainPresenter
import com.shepelevkirill.gallerytest.ui.scenes.new_photos.NewPhotosPresenter
import com.shepelevkirill.gallerytest.ui.scenes.photo.PhotoPresenter
import com.shepelevkirill.gallerytest.ui.scenes.popular_photos.PopularPhotosPresenter
import com.shepelevkirill.gallerytest.ui.adapters.NewPhotosAdapter
import com.shepelevkirill.gallerytest.ui.adapters.PopularPhotosAdapter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(GatewayModule::class)])
interface AppComponent {
    fun inject(target: MainPresenter)

    fun inject(target: NewPhotosPresenter)
    fun inject(target: NewPhotosAdapter)

    fun inject(target: PopularPhotosPresenter)
    fun inject(target: PopularPhotosAdapter)

    fun inject(target: PhotoPresenter)


    @Component.Builder
    interface Builder {
        fun build(): AppComponent
        fun retrofitModule(retrofit: RetrofitModule): Builder
        fun gatewayModule(gateway: GatewayModule): Builder
    }
}