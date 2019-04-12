package com.shepelevkirill.gallerytest.app.ui.scenes.upload

import android.net.Uri
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.shepelevkirill.gallerytest.app.App
import com.shepelevkirill.gallerytest.app.utils.getPath
import com.shepelevkirill.gallerytest.domain.gateway.AuthenticationGateway
import com.shepelevkirill.gallerytest.domain.usecases.core.photos.UploadPhotoUseCase
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
        val context = App.appContext

        val path = photo.getPath(context)
        selectedPhoto = File(path)

        viewState.showSelectedPhoto(selectedPhoto!!.name)
    }

    fun onGetInputData(title: String, description: String) {
        if (title == "") {
            viewState.showMessage("Please, specify title!")
            return
        }

        val photo: File? = selectedPhoto
        if (photo == null) {
            viewState.showMessage("Please, select image to upload!")
            return
        }

        uploadPhotoUseCase.uploadPhoto(title, description, photo)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { viewState.showProgressDialog() }
            .doFinally { viewState.hideProgressDialog() }
            .subscribe({ photoModel ->
                viewState.showPhotoUploadedDialog(photoModel)
                viewState.clearInputData()
            }, { error ->
                viewState.showMessage("Error during uploading image to server!")
                error.printStackTrace()
            })
            .let(compositeDisposable::add)
    }


    companion object {
        const val PRESENTER_TAG = "upload_presenter"
        const val PHOTO_PICKER_RC = 1
    }
}