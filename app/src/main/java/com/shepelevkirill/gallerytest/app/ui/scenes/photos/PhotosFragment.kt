package com.shepelevkirill.gallerytest.app.ui.scenes.photos

import android.content.Context
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
import com.shepelevkirill.gallerytest.app.App
import com.shepelevkirill.gallerytest.app.ui.adapters.PhotosAdapter
import com.shepelevkirill.gallerytest.app.ui.decorators.GridLayoutDecorator
import com.shepelevkirill.gallerytest.app.ui.dialogs.LoadingDialog
import com.shepelevkirill.gallerytest.app.ui.scenes.main.MainActivity
import com.shepelevkirill.gallerytest.app.ui.scenes.photo.PhotoFragment
import com.shepelevkirill.gallerytest.domain.models.PhotoModel
import kotlinx.android.synthetic.main.fragment_photos.*
import kotlinx.android.synthetic.main.fragment_photos.view.*
import java.io.Serializable

class PhotosFragment : MvpAppCompatFragmentX(), PhotosView {

    data class PhotosViewParams(val isNew: Boolean?, val isPopular: Boolean?): Serializable

    private lateinit var recyclerAdapter: PhotosAdapter
    private lateinit var photosViewParams: PhotosViewParams
    private lateinit var layoutManager: GridLayoutManager
    private val loadingDialog = LoadingDialog().apply {
        isCancelable = false
    }

    @InjectPresenter(type = PresenterType.GLOBAL)
    lateinit var presenter: PhotosPresenter

    @ProvidePresenter(type = PresenterType.GLOBAL)
    fun providePhotosPresenter(): PhotosPresenter {
        return App.appComponent.providePhotosPresenter().apply {
            this.isNew = photosViewParams.isNew
            this.isPopular = photosViewParams.isPopular
        }
    }

    @ProvidePresenterTag(presenterClass = PhotosPresenter::class, type = PresenterType.GLOBAL)
    fun providePhotosPresenterTag(): String {
        return "${PhotosPresenter.PRESENTER_TAG}_isNew=${photosViewParams.isNew}_isPopular=${photosViewParams.isPopular}"
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        photosViewParams = arguments?.getSerializable(PHOTOS_PARAMS_KEY) as PhotosViewParams
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_photos, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.ui_title.text = arguments?.getString(TITLE_KEY) ?: "Undefined"
        view.ui_swipeRefreshLayout.setOnRefreshListener(onRefreshListener)
        setupPhotosRecycler()
    }

    private fun setupPhotosRecycler() {
        val decorator = GridLayoutDecorator(this)
        layoutManager = GridLayoutManager(view?.context, 2)
        ui_photos.layoutManager = layoutManager
        recyclerAdapter = App.appComponent.providePhotosAdapter().apply {
            onPhotoClickedListener = presenter::onPhotoClicked
        }
        ui_photos.apply {
            setHasFixedSize(true)
            addOnScrollListener(onRecycleViewScrollListener)
            addItemDecoration(decorator)
            adapter = recyclerAdapter
        }
    }

    // Listener for RecycleView scroll
    private val onRecycleViewScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            val layoutManager = recyclerView.layoutManager as GridLayoutManager
            val lastVisibleItemPos = layoutManager.findLastVisibleItemPosition()

            // if we need to load new content



            if (lastVisibleItemPos + PhotosPresenter.ITEMS   _BUFFER >= layoutManager.itemCount - 1) {
                this@PhotosFragment.presenter.getPhotos()
            }
        }
    }

    private val onRefreshListener = SwipeRefreshLayout.OnRefreshListener {
        presenter.onRefresh()
    }

    override fun onResume() {
        super.onResume()
        presenter.onResume()
    }

    override fun addPhotos(photos: List<PhotoModel>) {
        recyclerAdapter.addPhotoModels(photos)
    }

    override fun clearPhotos() {
        recyclerAdapter.clearPhotoModels()
    }

    override fun highlightPhoto(id: Int) {
        recyclerAdapter.highlightPhoto(id)
    }

    override fun onPhotoClicked(photo: PhotoModel) {
        presenter.onPhotoClicked(photo)
    }

    override fun stopRefreshing() {
        view?.ui_swipeRefreshLayout?.isRefreshing = false
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

    override fun openPhotoView(photo: PhotoModel) {
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
        private const val TITLE_KEY = "title"
        private const val PHOTOS_PARAMS_KEY = "photos_params"

        fun newInstance(isNew: Boolean?, isPopular: Boolean?, title: String): PhotosFragment {
            val bundle = Bundle()
            val photosViewParams = PhotosViewParams(isNew, isPopular)
            bundle.putSerializable(PHOTOS_PARAMS_KEY, photosViewParams)
            bundle.putString(TITLE_KEY, title)

            val fragment = PhotosFragment()
            fragment.arguments = bundle

            return fragment
        }

        fun newInstance(photosViewParams: PhotosViewParams, title: String): PhotosFragment {
            val bundle = Bundle()
            bundle.putSerializable(PHOTOS_PARAMS_KEY, photosViewParams)
            bundle.putString(TITLE_KEY, title)

            val fragment = PhotosFragment()
            fragment.arguments = bundle

            return fragment
        }
    }
}