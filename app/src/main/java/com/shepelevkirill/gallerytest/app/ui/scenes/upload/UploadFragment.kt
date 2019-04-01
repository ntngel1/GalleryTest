package com.shepelevkirill.gallerytest.app.ui.scenes.upload

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.arellomobile.mvp.MvpFragmentX
import com.arellomobile.mvp.presenter.InjectPresenter
import com.shepelevkirill.gallerytest.R
import com.shepelevkirill.gallerytest.app.ui.dialogs.ImageUploadDialog
import kotlinx.android.synthetic.main.fragment_upload.*

class UploadFragment : MvpFragmentX(), UploadView {
    @InjectPresenter
    lateinit var presenter: UploadPresenter

    private val progressDialog = ImageUploadDialog().apply {
        isCancelable = false
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
        when (requestCode) {
            PHOTO_PICKER_RC -> {
                val photoUri = data?.data
                if (photoUri != null) {
                    presenter.onPhotoPicked(photoUri)
                }
            }
        }
    }

    override fun showMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun showPhotoPicker(intent: Intent, requestCode: Int) {
        PHOTO_PICKER_RC = requestCode
        startActivityForResult(intent, requestCode)
    }

    override fun showSelectedPhoto(filename: String) {
        selectedImageLabel.text = filename
    }

    override fun showProgressDialog() {
        progressDialog.show(fragmentManager!!, null)
    }

    override fun hideProgressDialog() {
        progressDialog.dismiss()
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
        var PHOTO_PICKER_RC = 0

        fun newInstance(): UploadFragment {
            val fragment = UploadFragment()

            val bundle = Bundle()
            fragment.arguments = bundle
            return fragment
        }
    }
}