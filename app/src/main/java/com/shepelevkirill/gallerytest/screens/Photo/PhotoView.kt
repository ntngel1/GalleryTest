package com.shepelevkirill.gallerytest.screens.Photo

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.shepelevkirill.core.models.PhotoModel
import com.shepelevkirill.gallerytest.R
import com.shepelevkirill.gallerytest.core.Photo
import kotlinx.android.synthetic.main.fragment_photo.*
import kotlinx.android.synthetic.main.fragment_photo.view.*

class PhotoView : Fragment(), Photo.View {
    private lateinit var presenter: Photo.Presenter
    private var photoModel: PhotoModel? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_photo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // On Back button clicked listener
        view.toolbar.setNavigationOnClickListener(onBackButtonPressedListener)

        // Setting up our title and description
        view.ui_title.text = photoModel?.name ?: "UNDEFINED"
        view.ui_description.text = photoModel?.description ?: "UNDEFINED"

        presenter.onViewCreated()
    }

    // Listener for Back button pressed
    private val onBackButtonPressedListener = View.OnClickListener {
        activity!!.supportFragmentManager.popBackStack()
    }

    override fun getPhotoModel(): PhotoModel = photoModel!!

    override fun showPhoto(photo: Bitmap) {
        ui_image.setImageBitmap(photo)
    }

    companion object {
        fun newInstance(image: PhotoModel): Fragment {
            val fragment = PhotoView()

            val args = Bundle()
            TODO("ADD PHOTO MODEL TO BUNDLE")
            fragment.arguments = args

            return fragment
        }
    }

}