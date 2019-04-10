package com.shepelevkirill.gallerytest.app.ui.scenes.photos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.arellomobile.moxy.MvpAppCompatFragmentX
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.PresenterType
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.arellomobile.mvp.presenter.ProvidePresenterTag
import com.shepelevkirill.gallerytest.R
import com.shepelevkirill.gallerytest.app.ui.decorators.GridLayoutDecorator
import com.shepelevkirill.gallerytest.app.ui.dialogs.LoadingDialog
import com.shepelevkirill.gallerytest.app.ui.scenes.main.MainActivity
import com.shepelevkirill.gallerytest.app.ui.scenes.photo.PhotoFragment
import com.shepelevkirill.gallerytest.domain.models.PhotoModel
import kotlinx.android.synthetic.main.fragment_photos.*
import kotlinx.android.synthetic.main.fragment_photos.view.*

class PhotosFragment : MvpAppCompatFragmentX(), PhotosView {
    @InjectPresenter(type = PresenterType.GLOBAL)
    lateinit var presenter: PhotosPresenter

    @ProvidePresenter(type = PresenterType.GLOBAL)
    fun providePhotosPresenter(): PhotosPresenter {
        val isNew = arguments?.getBoolean("isNew", false) ?: false
        val isPopular = arguments?.getBoolean("isPopular", false) ?: false
        return PhotosPresenter(isNew, isPopular)
    }

    @ProvidePresenterTag(presenterClass = PhotosPresenter::class, type = PresenterType.GLOBAL)
    fun providePhotosPresenterTag(): String {
        val isNew = arguments?.getBoolean("isNew", false) ?: false
        val isPopular = arguments?.getBoolean("isPopular", false) ?: false

        return "${PhotosPresenter.PRESENTER_TAG}_isNew=${isNew}_isPopular=${isPopular}"
    }

    private lateinit var layoutManager: GridLayoutManager
    private val loadingDialog = LoadingDialog().apply {
        isCancelable = false
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
        layoutManager = GridLayoutManager(view?.context, 2)
        ui_photos.layoutManager = layoutManager
        ui_photos.apply {
            setHasFixedSize(true)
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

    private val onRefreshListener = SwipeRefreshLayout.OnRefreshListener {
        presenter.onRefresh()
    }

    override fun onResume() {
        super.onResume()
        presenter.onResume()
    }

    override fun <VH : RecyclerView.ViewHolder> setAdapter(adapter: RecyclerView.Adapter<VH>) {
        ui_photos.adapter = adapter
    }

    override fun onPhotoClicked(photo: PhotoModel) {
        presenter.onPhotoClicked(photo)
    }

    override fun stopRefreshing() {
        view?.ui_swipeRefreshLayout?.isRefreshing = false
    }

    override fun showNetworkError() {
        presenter.clearPhotos()
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

    override fun openPhoto(photo: PhotoModel) {
        val fragment = PhotoFragment.newInstance(photo)
        // TODO Is it good or not?
        (activity as MainActivity).openScreenWithBackStack(fragment)
    }

    override fun onHighlightPhoto(photo: PhotoModel) {
        presenter.onHighlightPhoto(photo)
    }

    override fun showLoadingDialog() {
        loadingDialog.show(childFragmentManager, LOADING_DIALOG_TAG)
    }

    override fun hideLoadingDialog() {
        (childFragmentManager.findFragmentByTag(LOADING_DIALOG_TAG) as LoadingDialog).dismiss()
    }

    companion object {
        const val LOADING_DIALOG_TAG = "loading_dialog"

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