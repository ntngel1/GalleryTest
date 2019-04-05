package com.shepelevkirill.gallerytest.app.ui.dialogs.photo_uploaded_dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import com.arellomobile.mvp.MvpAppCompatDialogFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.shepelevkirill.gallerytest.app.ui.scenes.upload.OnShowPhotoListener
import com.shepelevkirill.gallerytest.domain.models.PhotoModel
import kotlinx.android.synthetic.main.dialog_photo_uploaded.*

class PhotoUploadedDialog : MvpAppCompatDialogFragment(), PhotoUploadedView {
    @InjectPresenter
    lateinit var presenter: PhotoUploadedPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(com.shepelevkirill.gallerytest.R.layout.dialog_photo_uploaded, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)

        showButton.setOnClickListener {
            presenter.onShowButtonClicked()
            if (activity is OnShowPhotoListener) {
                (activity as OnShowPhotoListener).onShowPhoto(presenter.photoModel)
            }
        }
    }

    override fun setPhotoModel(photoModel: PhotoModel) {
        onCreate(null)
        presenter.photoModel = photoModel
    }

    override fun close() {
        dismiss()
    }
}
