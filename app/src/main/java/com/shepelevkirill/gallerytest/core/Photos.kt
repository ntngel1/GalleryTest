package com.shepelevkirill.gallerytest.core

import com.shepelevkirill.core.models.PhotoModel

interface Photos {
    interface View {
        fun showPhoto(photo: PhotoModel)
        fun openPhoto(photo: PhotoModel)
    }
}