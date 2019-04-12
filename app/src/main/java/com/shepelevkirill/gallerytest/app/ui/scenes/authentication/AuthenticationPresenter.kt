package com.shepelevkirill.gallerytest.app.ui.scenes.authentication

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.shepelevkirill.gallerytest.domain.gateway.AuthenticationGateway
import com.shepelevkirill.gallerytest.domain.usecases.core.authentication.SignInUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@InjectViewState
class AuthenticationPresenter @Inject constructor(
    private val signInUseCase: SignInUseCase,
    private val authenticationGateway: AuthenticationGateway
) : MvpPresenter<AuthenticationView>() {

    private var username: String = ""
    private var password: String = ""
    private val compositeDisposable = CompositeDisposable()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        if (!authenticationGateway.isSignedIn()) {
            viewState.showSignInLayout()
        } else {
            viewState.showSignOutLayout()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }

    fun onSignInButtonClicked() {
        viewState.getSignInData()
        signIn()
    }

    fun onSignOutButtonClicked() {
        signOut()
        viewState.showSignInLayout()
    }

    fun onGetSignInData(username: String, password: String) {
        this.username = username
        this.password = password
    }

    private fun signIn() {
        signInUseCase.signIn(username, password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { viewState.showProgressDialog() }
            .doFinally { viewState.hideProgressDialog() }
            .subscribe({
                viewState.showSignOutLayout()
            }, { error ->
                viewState.showMessage("Error during signing in!")
                viewState.showSignInLayout()
                error.printStackTrace()
            })
            .let(compositeDisposable::add)
    }

    private fun signOut() {
        authenticationGateway.invalidateSession()
    }
}