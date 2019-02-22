package com.shepelevkirill.gallerytest.core

import androidx.recyclerview.widget.RecyclerView
import com.shepelevkirill.core.models.PhotoModel

interface NewPhotos {
    interface View : Photos.View {
        fun showNetworkError()
    }

    interface Presenter {
        fun attachView(view: View)
        fun detachView()

        fun onPhotoClicked(photo: PhotoModel)
        fun onRecyclerViewScrolled(recyclerView: RecyclerView, dx: Int, dy: Int)
        fun onRefresh()
    }
}