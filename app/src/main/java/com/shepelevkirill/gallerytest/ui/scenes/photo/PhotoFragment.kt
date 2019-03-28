package com.shepelevkirill.gallerytest.ui.scenes.photo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.arellomobile.mvp.MvpFragmentX
import com.arellomobile.mvp.presenter.InjectPresenter
import com.shepelevkirill.core.models.PhotoModel
import com.shepelevkirill.gallerytest.R
import com.shepelevkirill.gallerytest.ui.scenes.main.MainActivity
import com.shepelevkirill.gallerytest.utils.load
import kotlinx.android.synthetic.main.fragment_photo.*
import kotlinx.android.synthetic.main.fragment_photo.view.*

class PhotoFragment : MvpFragmentX(), PhotoView {
    @InjectPresenter
    lateinit var presenter: PhotoPresenter

    private var photoModel: PhotoModel? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        photoModel = arguments?.getSerializable("photo") as PhotoModel
        return inflater.inflate(R.layout.fragment_photo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // On Back button clicked listener
        view.toolbar.setNavigationOnClickListener(onBackButtonPressedListener)

        // Setting up our title and description
        view.ui_title.text = photoModel?.name ?: "UNDEFINED"
        view.ui_description.text = photoModel?.description ?: "UNDEFINED"

    }

    // Listener for Back button pressed
    private val onBackButtonPressedListener = View.OnClickListener {
        (activity as MainActivity).popFragment()
    }

    override fun showImage(url: String) {
        ui_image.load(url)
    }

    override fun getPhotoModel() {
        presenter.onGetPhotoModel(photoModel)
    }

    companion object {
        fun newInstance(image: PhotoModel): Fragment {
            val fragment = PhotoFragment()

            val args = Bundle()
            args.putSerializable("photo", image)
            fragment.arguments = args

            return fragment
        }
    }

}