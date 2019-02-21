package com.shepelevkirill.gallerytest

import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.shepelevkirill.gallerytest.server.Server

class NewPhotosFragment : PhotosFragment() {
    companion object {
        private const val FRAGMENT_TITLE = "New"

        fun newInstance(): NewPhotosFragment {
            val fragment = NewPhotosFragment()

            val args = Bundle()
            args.putString("title", FRAGMENT_TITLE)
            fragment.arguments = args

            return fragment
        }
    }

    override var contentLoader = { recyclerView: RecyclerView ->
        val page = recyclerView.adapter!!.itemCount / ITEMS_PER_PAGE + 1 // page we need to get
        Server.instance.getApi().getPhotos(page, ITEMS_PER_PAGE, true, null)
            .enqueue(requestCallback)
    }
}