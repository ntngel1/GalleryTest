package com.shepelevkirill.gallerytest.app.ui.routers.main

import androidx.fragment.app.Fragment
import com.shepelevkirill.gallerytest.app.ui.scenes.authentication.AuthenticationFragment
import com.shepelevkirill.gallerytest.app.ui.scenes.photos.PhotosFragment
import com.shepelevkirill.gallerytest.app.ui.scenes.upload.UploadFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

class NewPhotosScreen : SupportAppScreen() {
    val fragmentInstance = PhotosFragment.newInstance(true, null, "New")

    override fun getFragment(): Fragment {
        return fragmentInstance
    }
}

class PopularPhotosScreen : SupportAppScreen() {
    val fragmentInstance = PhotosFragment.newInstance(null, true, "Popular")

    override fun getFragment(): Fragment {
        return fragmentInstance
    }
}

class UploadScreen : SupportAppScreen() {
    val fragmentInstance = UploadFragment()

    override fun getFragment(): Fragment {
       return fragmentInstance
    }
}

class AuthenticationScreen : SupportAppScreen() {
    val fragmentInstance = AuthenticationFragment()

    override fun getFragment(): Fragment {
        return fragmentInstance
    }
}