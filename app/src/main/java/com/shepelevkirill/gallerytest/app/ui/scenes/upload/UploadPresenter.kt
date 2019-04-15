package com.shepelevkirill.gallerytest.app.ui.scenes.upload

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.shepelevkirill.gallerytest.domain.gateway.AuthenticationGateway
import com.shepelevkirill.gallerytest.domain.gateway.StorageGateway
import com.shepelevkirill.gallerytest.domain.usecases.core.photos.UploadPhotoUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.io.File
import java.net.URI
import javax.inject.Inject

@InjectViewState
class UploadPresenter @Inject constructor(
    private val uploadPhotoUseCase: UploadPhotoUseCase,
    private val authenticationGateway: AuthenticationGateway,
    private val storageGateway: StorageGateway
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

    fun onPhotoPicked(photo: URI) {
        storageGateway.getPhotoFile(photo.toString())
            .observeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe({ file ->
                selectedPhoto = file
                viewState.showSelectedPhoto(file.name)
            }, { error ->
                viewState.showMessage("Error during loading photo from storage! Try again!")
            })
            .let(compositeDisposable::add)
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