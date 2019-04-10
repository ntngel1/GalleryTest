package com.shepelevkirill.gallerytest.app.ui.scenes.photo

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

interface PhotoView : MvpView {
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showPhoto(url: String)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun getPhotoModel()
}