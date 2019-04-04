package com.shepelevkirill.gallerytest.app.ui.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import com.shepelevkirill.gallerytest.R
import com.shepelevkirill.gallerytest.domain.models.PhotoModel
import kotlinx.android.synthetic.main.dialog_photo_uploaded.*

class PhotoUploadedDialog : DialogFragment() {
    lateinit var photoModel: PhotoModel
    var onShowClickedListener: (() -> Unit)? = null
    var onOkClickedListener: (() -> Unit)? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_photo_uploaded, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)

        showButton.setOnClickListener {
            dismiss()
            onShowClickedListener?.invoke()
        }
        okButton.setOnClickListener {
            dismiss()
            onOkClickedListener?.invoke()
        }
    }
}