package com.shepelevkirill.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.shepelevkirill.gallery.server.response.photos.Image
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_photo.view.*

class PhotoFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_photo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // On Back button clicked listener
        view.toolbar.setNavigationOnClickListener(onBackButtonPressedListener)

        // Setting up our title and description
        view.ui_title.text = arguments!!.getString("title")
        view.ui_description.text = arguments!!.getString("description")

        // Loading image
        Picasso.get().load(arguments!!.getString("url"))
            .fit()
            .centerCrop()
            .into(view.ui_image)
    }

    // Listener for Back button pressed
    private val onBackButtonPressedListener = View.OnClickListener {
        activity!!.supportFragmentManager.popBackStack()
    }

    companion object {
        fun newInstance(image: Image): Fragment {
            val fragment = PhotoFragment()

            val args = Bundle()
            args.putString("title", image.name)
            args.putString("description", image.description)
            args.putString("url", image.image!!.getFullUrl())
            fragment.arguments = args

            return fragment
        }
    }

}