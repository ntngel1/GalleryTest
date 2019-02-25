package com.shepelevkirill.gallerytest.screens.new_photos

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shepelevkirill.core.models.PhotoModel
import com.shepelevkirill.gallerytest.R
import com.shepelevkirill.gallerytest.core.screens.NewPhotos
import com.shepelevkirill.gallerytest.screens.PhotosRecyclerViewAdapter
import com.shepelevkirill.gallerytest.screens.photo.PhotoView
import kotlinx.android.synthetic.main.fragment_new_photos.*
import kotlinx.android.synthetic.main.fragment_new_photos.view.*

class NewPhotosView : Fragment(), NewPhotos.View {
    override fun stopRefreshing() {
        view?.ui_swipeRefreshLayout?.isRefreshing = false
    }

    override fun clearPhotos() {
        recyclerAdapter.clear()
    }

    private var presenter: NewPhotos.Presenter = NewPhotosPresenter()
    private var recyclerAdapter: PhotosRecyclerViewAdapter = PhotosRecyclerViewAdapter(this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_new_photos, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.ui_photos.setHasFixedSize(true)
        view.ui_photos.layoutManager = GridLayoutManager(view.context, 2)
        view.ui_photos.adapter = recyclerAdapter
        view.ui_photos.setOnScrollListener(onRecycleViewScrollListener)
        view.ui_photos.addItemDecoration(recyclerViewMarginsDecorator)
        ui_swipeRefreshLayout.setOnRefreshListener(onRefreshListener)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.attachView(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    // Listener for RecycleView scroll
    private val onRecycleViewScrollListener = object: RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            presenter.onRecyclerViewScrolled(recyclerView, dx, dy)
        }
    }


    // RecyclerView decorator for items' spacing
    private val recyclerViewMarginsDecorator = object: RecyclerView.ItemDecoration() {
        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
            val position = parent.getChildLayoutPosition(view)

            // left element margine
            if (position % 2 == 0) outRect.right = (9 * resources.displayMetrics.density).toInt()

            // right element margine
            if (position % 2 != 0) outRect.left = (9 * resources.displayMetrics.density).toInt()

            // Top margin
            if (position != 0 && position != 1) outRect.top = (17 * resources.displayMetrics.density).toInt()

            // Last row bottom margin
            if (parent.adapter!!.itemCount % 2 == 0) // if last row is full (consists of two elements)
                if (position == parent.adapter!!.itemCount - 2 || position == parent.adapter!!.itemCount - 1)
                    outRect.bottom = (17 * resources.displayMetrics.density).toInt()

            if (parent.adapter!!.itemCount % 2 != 0) // if last row isn't full (consists of one element)
                if (position == parent.adapter!!.itemCount - 1)
                    outRect.bottom = (17 * resources.displayMetrics.density).toInt()
        }
    }

    private val onRefreshListener = {
        presenter.onRefresh()
    }

    override fun showNetworkError() {
        TODO("Show network error screen")
    }

    override fun showPhoto(photo: PhotoModel) {
        recyclerAdapter.add(photo)
        recyclerAdapter.notifyDataSetChanged()
    }

    override fun openPhoto(photo: PhotoModel) {
        activity!!.supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, PhotoView.newInstance(photo))
            .addToBackStack(null)
            .commit()
    }

    companion object {
        fun newInstance(): NewPhotosView {
            return NewPhotosView()
        }
    }

}