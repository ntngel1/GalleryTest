package com.shepelevkirill.gallerytest.ui.scenes.authentication

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.shepelevkirill.gallerytest.App
import io.reactivex.disposables.CompositeDisposable

@InjectViewState
class AuthenticationPresenter : MvpPresenter<AuthenticationView>() {
    private val compositeDisposable = CompositeDisposable()

    init {
        App.appComponent.inject(this)
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
}