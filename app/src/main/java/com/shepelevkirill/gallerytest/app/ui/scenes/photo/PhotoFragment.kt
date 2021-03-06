package com.shepelevkirill.gallerytest.app.ui.scenes.photo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.arellomobile.moxy.MvpAppCompatFragmentX
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.shepelevkirill.gallerytest.R
import com.shepelevkirill.gallerytest.app.App
import com.shepelevkirill.gallerytest.app.ui.scenes.main.MainActivity
import com.shepelevkirill.gallerytest.app.utils.load
import com.shepelevkirill.gallerytest.domain.models.PhotoModel
import kotlinx.android.synthetic.main.fragment_photo.*
import kotlinx.android.synthetic.main.fragment_photo.view.*

class PhotoFragment : MvpAppCompatFragmentX(), PhotoView {
    @InjectPresenter
    lateinit var presenter: PhotoPresenter

    @ProvidePresenter
    fun providePhotoPresenter(): PhotoPresenter {
        return App.appComponent.providePhotoPresenter()
    }

    private var photoModel: PhotoModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        photoModel = arguments?.getSerializable("photo") as PhotoModel
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_photo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.toolbar.setNavigationOnClickListener(onBackButtonPressedListener)

        view.ui_title.text = photoModel?.name ?: "Empty"
        view.ui_description.text = photoModel?.description ?: "Empty"
    }

    private val onBackButtonPressedListener = View.OnClickListener {
        (activity as MainActivity).popFragment()
    }

    override fun showPhoto(url: String) {
        ui_image.load(url)
    }

    override fun getPhotoModel() {
        presenter.onGetPhotoModel(photoModel)
    }

    companion object {
        fun newInstance(photoModel: PhotoModel): Fragment {
            val fragment = PhotoFragment()

            val args = Bundle()
            args.putSerializable("photo", photoModel)
            fragment.arguments = args

            return fragment
        }
    }
}