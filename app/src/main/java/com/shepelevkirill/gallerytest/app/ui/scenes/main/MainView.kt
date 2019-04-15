package com.shepelevkirill.gallerytest.app.ui.scenes.main

import androidx.fragment.app.Fragment
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

interface MainView : MvpView {

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun openDefaultScreen()

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun openScreen(fragment: Fragment)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun openScreenWithBackStack(fragment: Fragment)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun popFragment()

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun openNewPhotosView()
}