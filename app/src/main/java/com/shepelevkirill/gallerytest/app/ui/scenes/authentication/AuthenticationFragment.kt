package com.shepelevkirill.gallerytest.app.ui.scenes.authentication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.arellomobile.mvp.MvpFragmentX
import com.arellomobile.mvp.presenter.InjectPresenter
import com.shepelevkirill.gallerytest.R
import com.shepelevkirill.gallerytest.app.ui.dialogs.SigningInDialog
import kotlinx.android.synthetic.main.fragment_authentication.*

class AuthenticationFragment : MvpFragmentX(), AuthenticationView {
    @InjectPresenter
    lateinit var presenter: AuthenticationPresenter

    private val progressDialog = SigningInDialog().apply {
        isCancelable = false
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_authentication, container, false)
    }

    override fun onStart() {
        super.onStart()
        signInButton.setOnClickListener { presenter.onSignInButtonClicked() }
        signOutButton.setOnClickListener { presenter.onSignOutButtonClicked() }
    }

    override fun onResume() {
        super.onResume()
        presenter.onResume()
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
        progressDialog.show(fragmentManager!!, null)
    }

    override fun hideProgressDialog() {
        progressDialog.dismiss()
    }

    companion object {
        fun newInstance(): AuthenticationFragment {
            val fragment = AuthenticationFragment()

            val args = Bundle()
            fragment.arguments = args

            return fragment
        }
    }
}