package com.shepelevkirill.gallerytest.ui.scenes.authentication

import android.view.View
import com.arellomobile.mvp.MvpFragmentX
import com.arellomobile.mvp.presenter.InjectPresenter
import kotlinx.android.synthetic.main.fragment_authentication.*

class AuthenticationFragment : MvpFragmentX(), AuthenticationView {
    @InjectPresenter
    lateinit var presenter: AuthenticationPresenter

    override fun showSignInLayout() {
        signOutLayout.visibility = View.GONE
        signInLayout.visibility = View.VISIBLE
    }

    override fun showSignOutLayout() {
        signOutLayout.visibility = View.VISIBLE
        signInLayout.visibility = View.GONE
    }

}