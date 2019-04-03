package com.shepelevkirill.gallerytest.app.ui.scenes.upload

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.core.net.toFile
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.shepelevkirill.gallerytest.app.App
import com.shepelevkirill.gallerytest.app.utils.getPath
import com.shepelevkirill.gallerytest.domain.gateway.AuthenticationGateway
import com.shepelevkirill.gallerytest.domain.gateway.MediaObjectGateway
import com.shepelevkirill.gallerytest.domain.usecases.photos.UploadPhotoUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.io.File
import java.net.URI
import javax.inject.Inject

@InjectViewState
class UploadPresenter : MvpPresenter<UploadView>() {
    @Inject
    lateinit var uploadPhotoUseCase: UploadPhotoUseCase
    @Inject
    lateinit var authenticationGateway: AuthenticationGateway

    private val compositeDisposable = CompositeDisposable()
    private var selectedPhoto: File? = null

    init {
        App.appComponent.inject(this)
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

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
                viewState.showPhotoUploadedDialog()
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
        const val PHOTO_PICKER_RC = 1
    }
}