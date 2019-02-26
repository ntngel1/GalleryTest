package com.shepelevkirill.gallerytest

import com.shepelevkirill.gallerytest.data.GatewayModule
import com.shepelevkirill.gallerytest.data.RetrofitModule
import com.shepelevkirill.gallerytest.screens.main.MainPresenter
import com.shepelevkirill.gallerytest.screens.new_photos.NewPhotosPresenter
import com.shepelevkirill.gallerytest.screens.photo.PhotoPresenter
import com.shepelevkirill.gallerytest.screens.popular_photos.PopularPhotosPresenter
import com.shepelevkirill.gallerytest.screens.new_photos.RecyclerViewAdapter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(GatewayModule::class)])
interface AppComponent {
    fun inject(target: MainPresenter)

    fun inject(target: NewPhotosPresenter)
    fun inject(target: RecyclerViewAdapter)

    fun inject(target: PopularPhotosPresenter)
    fun inject(target: com.shepelevkirill.gallerytest.screens.popular_photos.RecyclerViewAdapter)

    fun inject(target: PhotoPresenter)


    @Component.Builder
    interface Builder {
        fun build(): AppComponent
        fun retrofitModule(retrofit: RetrofitModule): Builder
        fun gatewayModule(gateway: GatewayModule): Builder
    }
}