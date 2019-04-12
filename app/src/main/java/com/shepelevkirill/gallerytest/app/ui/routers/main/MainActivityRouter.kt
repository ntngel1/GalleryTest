package com.shepelevkirill.gallerytest.app.ui.routers.main

import androidx.fragment.app.FragmentActivity
import com.shepelevkirill.gallerytest.app.ui.scenes.photo.PhotoFragment
import com.shepelevkirill.gallerytest.app.ui.scenes.photos.PhotosFragment
import com.shepelevkirill.gallerytest.domain.models.PhotoModel
import java.lang.ref.WeakReference

class MainActivityRouter(private val activity: WeakReference<FragmentActivity>, private val fragmentContainerId: Int) : MainRouter {

    private val newPhotosFragment = PhotosFragment.newInstance(true, null, "New")
    private val popularPhotosFragment = PhotosFragment.newInstance(true, null, "New")
    private val supportFragmentManager
        get() = activity.get()?.supportFragmentManager


    override fun openPhotoView(photo: PhotoModel) {
        val fragment = PhotoFragment.newInstance(photo)

        supportFragmentManager?.beginTransaction()
            ?.replace(fragmentContainerId, fragment)
            ?.commit()
    }

    override fun openNewPhotosView() {

    }

    override fun openPopularPhotosView() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun openAuthenticationView() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun openUploadPhotoView() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}