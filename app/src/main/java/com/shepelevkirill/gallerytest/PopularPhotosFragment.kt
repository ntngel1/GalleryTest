package com.shepelevkirill.gallerytest

import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.shepelevkirill.gallerytest.server.Server

class PopularPhotosFragment : PhotosFragment() {
    companion object {
        private const val FRAGMENT_TITLE = "Popular"

        fun newInstance(): PopularPhotosFragment {
            val fragment = PopularPhotosFragment()

            val args = Bundle()
            args.putString("title", FRAGMENT_TITLE)
            fragment.arguments = args

            return fragment
        }
    }

    override var contentLoader: ContentLoader = object : ContentLoader {
        override fun LoadContent(recyclerView: RecyclerView) {
            val page = recyclerView.adapter!!.itemCount / ITEMS_PER_PAGE + 1 // page we need to get
            Server.instance.getApi().getPhotos(page, ITEMS_PER_PAGE, null, true)
                .enqueue(requestCallback)
        }
    }
}