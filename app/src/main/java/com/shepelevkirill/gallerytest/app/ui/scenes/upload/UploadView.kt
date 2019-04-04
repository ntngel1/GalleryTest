package com.shepelevkirill.gallerytest.app.ui.scenes.upload

import android.content.Intent
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.shepelevkirill.gallerytest.domain.models.PhotoModel

interface UploadView : MvpView {
    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showMessage(message: String)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showPhotoPicker()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showSelectedPhoto(filename: String)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showProgressDialog()

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun hideProgressDialog()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showSignInMessageLayout()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun hideSignInMessageLayout()

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showPhotoUploadedDialog(photoModel: PhotoModel)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun getInputData()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun clearInputData()
}