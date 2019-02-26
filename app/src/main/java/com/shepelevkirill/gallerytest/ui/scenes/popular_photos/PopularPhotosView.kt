package com.shepelevkirill.gallerytest.ui.scenes.popular_photos

import androidx.recyclerview.widget.RecyclerView
import com.shepelevkirill.core.models.PhotoModel

interface PopularPhotosView {
    interface View {
        fun showPhoto(photo: PhotoModel)
        fun openPhoto(photo: PhotoModel)
        fun clearPhotos()
        fun showNetworkError()
        fun hideNetworkError()
        fun showProgress()
        fun hideProgress()
        fun stopRefreshing()

        fun onPhotoClicked(photo: PhotoModel)
        fun onOpen()
    }

    interface Presenter {
        fun attachView(view: View)
        fun detachView()

        fun onCreate()
        fun onDestroy()
        fun onOpen()

        fun onPhotoClicked(photo: PhotoModel)
        fun onRecyclerViewScrolled(recyclerView: RecyclerView, dx: Int, dy: Int)
        fun onRefresh()
    }
}