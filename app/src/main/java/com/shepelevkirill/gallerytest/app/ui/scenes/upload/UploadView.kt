package com.shepelevkirill.gallerytest.app.ui.scenes.upload

import android.content.Intent
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

interface UploadView : MvpView {
    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showMessage(message: String)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showPhotoPicker(intent: Intent, requestCode: Int)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showSelectedPhoto(filename: String)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showProgressDialog()

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun hideProgressDialog()

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showPhotoUploadedDialog()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun getInputData()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun clearInputData()
}