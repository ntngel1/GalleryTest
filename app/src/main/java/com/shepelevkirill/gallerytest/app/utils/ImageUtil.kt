package com.shepelevkirill.gallerytest.app.utils

import android.widget.ImageView
import com.squareup.picasso.Picasso

fun ImageView.loadThumbnail(url: String) {
    Picasso.get()
        .load(url)
        .fit()
        .centerCrop()
        .into(this)
}

fun ImageView.load(url: String) {
    Picasso.get()
        .load(url)
        .into(this)
}

fun ImageView.stopLoading() {
    Picasso.get().cancelRequest(this)
}