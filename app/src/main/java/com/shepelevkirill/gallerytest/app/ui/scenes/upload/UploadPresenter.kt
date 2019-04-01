package com.shepelevkirill.gallerytest.app.ui.scenes.upload

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.core.net.toFile
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.shepelevkirill.gallerytest.app.App
import com.shepelevkirill.gallerytest.app.utils.getPath
import com.shepelevkirill.gallerytest.domain.gateway.MediaObjectGateway
import com.shepelevkirill.gallerytest.domain.usecases.photos.UploadPhotoUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.File
import java.net.URI
import javax.inject.Inject

@InjectViewState
class UploadPresenter : MvpPresenter<UploadView>() {
    @Inject
    lateinit var uploadPhotoUseCase: UploadPhotoUseCase

    private val photoPickerIntent = Intent(Intent.ACTION_PICK).apply {
        type = "image/*"
    }
    private var selectedPhoto: File? = null

    init {
        App.appComponent.inject(this)
    }

    fun onSelectPhotoButtonClicked() {
        viewState.showPhotoPicker(photoPickerIntent, PHOTO_PICKER_RC)
    }

    fun onClearButtonClicked() {
        viewState.clearInputData()
        selectedPhoto = null
    }

    fun onUploadButtonClicked() {
        viewState.getInputData()
    }

    fun onPhotoPicked(photo: Uri) {
        val path = photo.getPath(App.applicationContext!!)
        selectedPhoto = File(path)

        viewState.showSelectedPhoto(selectedPhoto!!.name)
    }

    fun onGetInputData(title: String, description: String) {
        if (title == "") {
            viewState.showMessage("Please, specify title!")
            return
        }
        if (selectedPhoto == null) {
            viewState.showMessage("Please, select image to upload!")
            return
        }

        viewState.showProgressDialog()
        uploadPhotoUseCase.setSchedulers(Schedulers.io(), AndroidSchedulers.mainThread())
            .execute(UploadPhotoUseCase.Params(title, description, selectedPhoto!!))
            .subscribe({
                viewState.showMessage("Image uploaded!")
                viewState.clearInputData()
                viewState.hideProgressDialog()
            }, {
                viewState.hideProgressDialog()
                viewState.showMessage("Error during uploading image to server!")
                it.printStackTrace()
            })
    }

    companion object {
        const val PHOTO_PICKER_RC = 1
    }
}