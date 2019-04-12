package com.shepelevkirill.gallerytest.app.ui.scenes.authentication

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

interface AuthenticationView : MvpView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showSignInLayout()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showSignOutLayout()

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun getSignInData()

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showMessage(message: String)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showProgressDialog()

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun hideProgressDialog()
}