package com.shepelevkirill.gallerytest.app.ui.scenes.upload

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.arellomobile.mvp.MvpFragmentX
import com.arellomobile.mvp.presenter.InjectPresenter
import com.shepelevkirill.gallerytest.R
import com.shepelevkirill.gallerytest.app.ui.dialogs.PhotoUploadingDialog
import com.shepelevkirill.gallerytest.app.ui.dialogs.PhotoUploadedDialog
import kotlinx.android.synthetic.main.fragment_upload.*

class UploadFragment : MvpFragmentX(), UploadView {
    @InjectPresenter
    lateinit var presenter: UploadPresenter

    private val photoPickerIntent = Intent(Intent.ACTION_PICK).apply {
        type = "image/*"
    }
    private val progressDialog = PhotoUploadingDialog().apply {
        isCancelable = false
    }
    private val photoUploadedDialog = PhotoUploadedDialog().apply {
        onShowClickedListener = { showMessage("Show Clicked!") }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_upload, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        selectPhotoButton.setOnClickListener { presenter.onSelectPhotoButtonClicked() }
        clearButton.setOnClickListener { presenter.onClearButtonClicked() }
        uploadButton.setOnClickListener { presenter.onUploadButtonClicked() }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PHOTO_PICKER_RC) {
            val photoUri = data?.data
            if (photoUri != null) {
                presenter.onPhotoPicked(photoUri)
            }
        }
    }

    override fun showMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun showPhotoPicker() {
        startActivityForResult(photoPickerIntent, PHOTO_PICKER_RC)
    }

    override fun showSelectedPhoto(filename: String) {
        selectedImageLabel.text = filename
    }

    override fun showProgressDialog() {
        progressDialog.show(childFragmentManager, PHOTO_UPLOADING_DIALOG_TAG)
    }

    override fun hideProgressDialog() {
        childFragmentManager.findFragmentByTag(PHOTO_UPLOADING_DIALOG_TAG).let {
            (it as DialogFragment).dismiss()
        }
    }

    override fun showSignInMessageLayout() {
        SignInMessageLayout.visibility = View.VISIBLE
        uploadLayout.visibility = View.GONE
    }

    override fun hideSignInMessageLayout() {
        SignInMessageLayout.visibility = View.GONE
        uploadLayout.visibility = View.VISIBLE
    }

    override fun showPhotoUploadedDialog() {
        photoUploadedDialog.show(childFragmentManager, PHOTO_UPLOADED_DIALOG_TAG)
    }

    override fun getInputData() {
        presenter.onGetInputData(titleInput.text.toString(), descriptionInput.text.toString())
    }

    override fun clearInputData() {
        titleInput.text.clear()
        descriptionInput.text.clear()
        selectedImageLabel.text = ""
    }

    companion object {
        const val PHOTO_PICKER_RC = 1
        const val PHOTO_UPLOADING_DIALOG_TAG = "photo_uploading_dialog"
        const val PHOTO_UPLOADED_DIALOG_TAG = "photo_uploaded_dialog"

        fun newInstance(): UploadFragment {
            val fragment = UploadFragment()

            val bundle = Bundle()
            fragment.arguments = bundle
            return fragment
        }
    }
}