package com.shepelevkirill.gallerytest.ui.scenes.photos

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.shepelevkirill.core.models.PhotoModel
import com.shepelevkirill.gallerytest.R
import com.shepelevkirill.gallerytest.ui.adapters.PhotosAdapter
import com.shepelevkirill.gallerytest.ui.decorators.GridLayoutDecorator
import com.shepelevkirill.gallerytest.ui.scenes.photo.PhotoFragment
import kotlinx.android.synthetic.main.fragment_photos.view.*

class PhotosFragment : Fragment(), PhotosView.View {
    private var presenter: PhotosView.Presenter? = null
    private var recyclerAdapter: PhotosAdapter = PhotosAdapter(this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_photos, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.ui_photos.setHasFixedSize(true)
        view.ui_photos.layoutManager = GridLayoutManager(view.context, 2)
        view.ui_photos.adapter = recyclerAdapter
        view.ui_photos.setOnScrollListener(onRecycleViewScrollListener)
        view.ui_photos.addItemDecoration(GridLayoutDecorator(this))
        view.ui_swipeRefreshLayout.setOnRefreshListener(onRefreshListener)
        presenter?.onCreate()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val isPopular: Boolean? = if (arguments?.getBoolean("isPopular") == true) true else null
        val isNew: Boolean? = if (arguments?.getBoolean("isNew") == true) true else null

        presenter = PhotosPresenter(isNew, isPopular)
        presenter?.attachView(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter?.onDestroy()
        presenter?.detachView()
    }

    // Listener for RecycleView scroll
    private val onRecycleViewScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            presenter?.onRecyclerViewScrolled(recyclerView, dx, dy)
        }
    }

    private val onRefreshListener = SwipeRefreshLayout.OnRefreshListener { presenter?.onRefresh() }

    override fun onPhotoClicked(photo: PhotoModel) {
        presenter?.onPhotoClicked(photo)
    }

    override fun onOpen() {
        presenter?.onOpen()
    }

    override fun stopRefreshing() {
        view?.ui_swipeRefreshLayout?.isRefreshing = false
    }

    override fun clearPhotos() {
        recyclerAdapter.clear()
    }

    override fun showNetworkError() {
        clearPhotos()
        view!!.ui_photos.visibility = View.INVISIBLE
        view!!.ui_network_error_layout.visibility = View.VISIBLE
        view!!.ui_progressbar.visibility = View.INVISIBLE
        stopRefreshing()
    }

    override fun hideNetworkError() {
        view!!.ui_network_error_layout.visibility = View.INVISIBLE
        view!!.ui_photos.visibility = View.VISIBLE
    }

    override fun showProgress() {
        view!!.ui_progressbar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        view!!.ui_progressbar.visibility = View.INVISIBLE
    }

    override fun showPhoto(photo: PhotoModel) {
        recyclerAdapter.add(photo)
        recyclerAdapter.notifyDataSetChanged()
    }

    override fun openPhoto(photo: PhotoModel) {
        activity!!.supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, PhotoFragment.newInstance(photo))
            .addToBackStack(null)
            .commit()
    }


    companion object {
        fun newInstance(isNew: Boolean?, isPopular: Boolean?): PhotosFragment {
            val bundle = Bundle()
            /* TODO So here is just boolean type, not nullable
            * TODO It does matter
             */
            if (isNew != null)
                bundle.putBoolean("isNew", isNew)
            if (isPopular != null)
                bundle.putBoolean("isPopular", isPopular)
            val fragment = PhotosFragment()
            fragment.arguments = bundle

            return fragment
        }
    }
}