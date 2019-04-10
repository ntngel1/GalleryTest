package com.shepelevkirill.gallerytest.app.ui.scenes.authentication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.arellomobile.moxy.MvpAppCompatFragmentX
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.shepelevkirill.gallerytest.R
import com.shepelevkirill.gallerytest.app.App
import com.shepelevkirill.gallerytest.app.ui.dialogs.SigningInDialog
import kotlinx.android.synthetic.main.fragment_authentication.*

class AuthenticationFragment : MvpAppCompatFragmentX(), AuthenticationView {
    @InjectPresenter
    lateinit var presenter: AuthenticationPresenter

    @ProvidePresenter
    fun provideAuthenticationPresenter(): AuthenticationPresenter {
        return App.appComponent.provideAuthenticationPresenter()
    }

    private val progressDialog = SigningInDialog().apply {
        isCancelable = false
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_authentication, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        signInButton.setOnClickListener { presenter.onSignInButtonClicked() }
        signOutButton.setOnClickListener { presenter.onSignOutButtonClicked() }
    }

    override fun showSignInLayout() {
        clearSignInData()
        signOutLayout.visibility = View.GONE
        signInLayout.visibility = View.VISIBLE
    }

    private fun clearSignInData() {
        loginInput.text.clear()
        passwordInput.text.clear()
    }

    override fun showSignOutLayout() {
        signOutLayout.visibility = View.VISIBLE
        signInLayout.visibility = View.GONE
    }

    override fun getSignInData() {
        presenter.onGetSignInData(loginInput.text.toString(), passwordInput.text.toString())
    }

    override fun showMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun showProgressDialog() {
        progressDialog.show(childFragmentManager, SIGNING_IN_DIALOG_TAG)
    }

    override fun hideProgressDialog() {
        childFragmentManager.findFragmentByTag(SIGNING_IN_DIALOG_TAG)
            ?.let { (it as DialogFragment).dismiss() }
    }

    companion object {
        const val SIGNING_IN_DIALOG_TAG = "signing_in_dialog"

        fun newInstance(): AuthenticationFragment {
            val fragment = AuthenticationFragment()

            val args = Bundle()
            fragment.arguments = args

            return fragment
        }
    }
}