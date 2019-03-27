package com.shepelevkirill.gallerytest.ui.scenes.photos

import androidx.recyclerview.widget.RecyclerView
import com.arellomobile.mvp.MvpView
import com.shepelevkirill.core.models.PhotoModel

interface PhotosView : MvpView {
    fun showPhoto(photo: PhotoModel)
    fun openPhoto(photo: PhotoModel)
    fun clearPhotos()
    fun showNetworkError()
    fun hideNetworkError()
    fun showProgress()
    fun hideProgress()
    fun stopRefreshing()

    fun onPhotoClicked(photo: PhotoModel)
}