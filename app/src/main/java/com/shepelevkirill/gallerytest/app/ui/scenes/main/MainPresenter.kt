package com.shepelevkirill.gallerytest.app.ui.scenes.main

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import javax.inject.Inject

@InjectViewState
class MainPresenter @Inject constructor() : MvpPresenter<MainView>() {
    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.openDefaultScreen()
    }
}