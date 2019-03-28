package com.shepelevkirill.gallerytest.ui.scenes.photo

import android.widget.ImageView
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.shepelevkirill.core.models.PhotoModel
import com.squareup.picasso.RequestCreator

interface PhotoView : MvpView {
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showImage(url: String)
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun getPhotoModel()
}