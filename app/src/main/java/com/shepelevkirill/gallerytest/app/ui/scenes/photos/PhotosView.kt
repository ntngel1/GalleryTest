package com.shepelevkirill.gallerytest.app.ui.scenes.photos

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.shepelevkirill.gallerytest.domain.models.PhotoModel

interface PhotosView : MvpView {

    @StateStrategyType(AddToEndStrategy::class)
    fun addPhotos(photos: List<PhotoModel>)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun clearPhotos()

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun highlightPhoto(id: Int)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun openPhotoView(photo: PhotoModel)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showNetworkError()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun hideNetworkError()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showProgress()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun hideProgress()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun stopRefreshing()

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun onPhotoClicked(photo: PhotoModel)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun onHighlightPhoto(photo: PhotoModel)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showLoadingDialog()

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun hideLoadingDialog()
}