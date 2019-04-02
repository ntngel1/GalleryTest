package com.shepelevkirill.gallerytest.app.ui.scenes.photos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.arellomobile.mvp.MvpFragmentX
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.shepelevkirill.gallerytest.domain.models.PhotoModel
import com.shepelevkirill.gallerytest.R
import com.shepelevkirill.gallerytest.app.ui.adapters.PhotosAdapter
import com.shepelevkirill.gallerytest.app.ui.decorators.GridLayoutDecorator
import com.shepelevkirill.gallerytest.app.ui.scenes.main.MainActivity
import com.shepelevkirill.gallerytest.app.ui.scenes.photo.PhotoFragment
import kotlinx.android.synthetic.main.fragment_photos.*
import kotlinx.android.synthetic.main.fragment_photos.view.*

class PhotosFragment : MvpFragmentX(), PhotosView {
    @InjectPresenter
    lateinit var presenter: PhotosPresenter

    private var recyclerAdapter: PhotosAdapter = PhotosAdapter(this)

    @ProvidePresenter
    fun providePhotosPresenter(): PhotosPresenter {
        val isNew = arguments?.getBoolean("isNew", false) ?: false
        val isPopular = arguments?.getBoolean("isPopular", false) ?: false
        return PhotosPresenter(isNew, isPopular)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_photos, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.ui_title.text = arguments?.getString("title") ?: "Undefined"
        view.ui_swipeRefreshLayout.setOnRefreshListener(onRefreshListener)
        setupPhotosRecycler()
    }

    private fun setupPhotosRecycler() {
        val decorator = GridLayoutDecorator(this)
        val layoutManager = GridLayoutManager(view?.context, 2)
        ui_photos.apply {
            setHasFixedSize(true)
            this.layoutManager = layoutManager
            adapter = recyclerAdapter
            setOnScrollListener(onRecycleViewScrollListener)
            addItemDecoration(decorator)
        }
    }

    // Listener for RecycleView scroll
    private val onRecycleViewScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            presenter.onRecyclerViewScrolled(recyclerView, dx, dy)
        }
    }

    private val onRefreshListener = SwipeRefreshLayout.OnRefreshListener { presenter.onRefresh() }

    override fun onResume() {
        super.onResume()
        presenter.onResume()
    }

    override fun onPhotoClicked(photo: PhotoModel) {
        presenter.onPhotoClicked(photo)
    }

    override fun stopRefreshing() {
        view?.ui_swipeRefreshLayout?.isRefreshing = false
    }

    override fun clearPhotos() {
        recyclerAdapter.clearPhotoModels()
    }

    override fun showNetworkError() {
        clearPhotos()
        view?.ui_photos?.visibility = View.INVISIBLE
        view?.ui_network_error_layout?.visibility = View.VISIBLE
        view?.ui_progressbar?.visibility = View.INVISIBLE
        stopRefreshing()
    }

    override fun hideNetworkError() {
        view?.ui_network_error_layout?.visibility = View.INVISIBLE
        view?.ui_photos?.visibility = View.VISIBLE
    }

    override fun showProgress() {
        view?.ui_progressbar?.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        view?.ui_progressbar?.visibility = View.INVISIBLE
    }

    override fun addPhoto(photo: PhotoModel) {
        recyclerAdapter.addPhotoModel(photo)
        recyclerAdapter.notifyDataSetChanged()
    }

    override fun openPhoto(photo: PhotoModel) {
        val fragment = PhotoFragment.newInstance(photo)
        // TODO Is it good or not?
        (activity as MainActivity).openScreenWithBackStack(fragment)
    }


    companion object {
        fun newInstance(isNew: Boolean, isPopular: Boolean, title: String): PhotosFragment {
            val bundle = Bundle()
            bundle.putBoolean("isNew", isNew)
            bundle.putBoolean("isPopular", isPopular)
            bundle.putString("title", title)

            val fragment = PhotosFragment()
            fragment.arguments = bundle

            return fragment
        }
    }
}