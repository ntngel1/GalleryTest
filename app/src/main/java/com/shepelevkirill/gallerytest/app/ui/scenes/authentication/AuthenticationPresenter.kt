package com.shepelevkirill.gallerytest.app.ui.scenes.authentication

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.shepelevkirill.gallerytest.app.App
import com.shepelevkirill.gallerytest.domain.gateway.AuthenticationGateway
import com.shepelevkirill.gallerytest.domain.gateway.UserGateway
import com.shepelevkirill.gallerytest.domain.usecases.authentication.SignInUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@InjectViewState
class AuthenticationPresenter : MvpPresenter<AuthenticationView>() {
    @Inject
    lateinit var signInUseCase: SignInUseCase
    @Inject
    lateinit var userGateway: UserGateway
    @Inject
    lateinit var authenticationGateway: AuthenticationGateway

    private val compositeDisposable = CompositeDisposable()

    init {
        App.appComponent.inject(this)
    }

    fun onResume() {
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
    }

    fun onSignOutButtonClicked() {
        authenticationGateway.invalidateSession()
        viewState.showSignInLayout()
    }

    fun onGetSignInData(username: String, password: String) {
        viewState.showProgressDialog()

        signInUseCase.setSchedulers(Schedulers.io(), AndroidSchedulers.mainThread())
            .execute(SignInUseCase.Params(username, password))
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
}