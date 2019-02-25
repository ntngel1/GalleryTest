package com.shepelevkirill.gallerytest.core.screens

import androidx.recyclerview.widget.RecyclerView
import com.shepelevkirill.core.models.PhotoModel

interface PopularPhotos {
    interface View  {
        fun showPhoto(photo: PhotoModel)
        fun openPhoto(photo: PhotoModel)
        fun clearPhotos()
        fun showNetworkError()
        fun hideNetworkError()
        fun stopRefreshing()

        fun onPhotoClicked(photo: PhotoModel)
    }

    interface Presenter {
        fun attachView(view: View)
        fun detachView()

        fun onCreate()

        fun onPhotoClicked(photo: PhotoModel)
        fun onRecyclerViewScrolled(recyclerView: RecyclerView, dx: Int, dy: Int)
        fun onRefresh()
    }
}