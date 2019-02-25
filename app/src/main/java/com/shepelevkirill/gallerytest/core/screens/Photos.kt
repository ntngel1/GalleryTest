package com.shepelevkirill.gallerytest.core.screens

import com.shepelevkirill.core.models.PhotoModel
import com.shepelevkirill.gallerytest.screens.PhotosRecyclerViewAdapter

interface Photos {
    interface View {
        fun showPhoto(photo: PhotoModel)
        fun openPhoto(photo: PhotoModel)
        fun clearPhotos()
    }
}