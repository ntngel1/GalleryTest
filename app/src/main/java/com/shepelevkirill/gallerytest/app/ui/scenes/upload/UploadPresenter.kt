package com.shepelevkirill.gallerytest.app.ui.scenes.upload

import android.net.Uri
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.shepelevkirill.gallerytest.app.App
import com.shepelevkirill.gallerytest.app.utils.getPath
import com.shepelevkirill.gallerytest.domain.gateway.AuthenticationGateway
import com.shepelevkirill.gallerytest.domain.usecases.photos.UploadPhotoUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.io.File
import javax.inject.Inject

@InjectViewState
class UploadPresenter @Inject constructor(
    private val uploadPhotoUseCase: UploadPhotoUseCase,
    private val authenticationGateway: AuthenticationGateway
) : MvpPresenter<UploadView>() {

    private var selectedPhoto: File? = null
    private val compositeDisposable = CompositeDisposable()

    fun onResume() {
        if (authenticationGateway.isSignedIn()) {
            viewState.hideSignInMessageLayout()
        } else {
            viewState.showSignInMessageLayout()
        }
    }

    fun onSelectPhotoButtonClicked() {
        viewState.showPhotoPicker()
    }

    fun onClearButtonClicked() {
        viewState.clearInputData()
        selectedPhoto = null
    }

    fun onUploadButtonClicked() {
        viewState.getInputData()
    }

    fun onPhotoPicked(photo: Uri) {
        val path = photo.getPath(App.applicationContext!!) // TODO Fix this piece of shit.
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
            .subscribe({ photoModel ->
                viewState.showPhotoUploadedDialog(photoModel)
                viewState.clearInputData()
                viewState.hideProgressDialog()
            }, {
                viewState.hideProgressDialog()
                viewState.showMessage("Error during uploading image to server!")
                it.printStackTrace()
            })
            .let(compositeDisposable::add)
    }

    companion object {
        const val PRESENTER_TAG = "upload_presenter"
        const val PHOTO_PICKER_RC = 1
    }
}