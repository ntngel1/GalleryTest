package com.shepelevkirill.gallerytest.ui.scenes.authentication

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

interface AuthenticationView : MvpView {
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showSignInLayout()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showSignOutLayout()
}