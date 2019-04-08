package com.shepelevkirill.gallerytest.app.ui.scenes.authentication

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.shepelevkirill.gallerytest.domain.gateway.AuthenticationGateway
import com.shepelevkirill.gallerytest.domain.usecases.authentication.SignInUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@InjectViewState
class AuthenticationPresenter @Inject constructor(
    private val signInUseCase: SignInUseCase,
    private val authenticationGateway: AuthenticationGateway
) : MvpPresenter<AuthenticationView>() {

    private lateinit var signInParams: SignInUseCase.Params
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
        signInParams = SignInUseCase.Params(username, password)
    }

    private fun signIn() {
        viewState.showProgressDialog()
        signInUseCase.setSchedulers(Schedulers.io(), AndroidSchedulers.mainThread())
            .execute(signInParams)
            .subscribe({
                viewState.showSignOutLayout()
                viewState.hideProgressDialog()
            }, {
                it.printStackTrace()
                viewState.showMessage("Error during signing in!")
                viewState.hideProgressDialog()
            })
            .let(compositeDisposable::add)
    }

    private fun signOut() {
        authenticationGateway.invalidateSession()
    }
}