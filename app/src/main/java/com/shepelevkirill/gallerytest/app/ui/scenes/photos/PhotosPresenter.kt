package com.shepelevkirill.gallerytest.app.ui.scenes.photos

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.shepelevkirill.gallerytest.app.App
import com.shepelevkirill.gallerytest.app.ui.adapters.PhotosAdapter
import com.shepelevkirill.gallerytest.domain.gateway.NetworkGateway
import com.shepelevkirill.gallerytest.domain.gateway.PhotoGateway
import com.shepelevkirill.gallerytest.domain.models.PhotoModel
import com.shepelevkirill.gallerytest.domain.models.PhotosModel
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@InjectViewState
class PhotosPresenter constructor(isNew: Boolean, isPopular: Boolean) : MvpPresenter<PhotosView>() {

    private val recyclerAdapter: PhotosAdapter = PhotosAdapter().apply {
        onPhotoClickedListener = this@PhotosPresenter::onPhotoClicked
    }
    private var currentPage: Int = 0
    private var isGetPhotosRequestSent = false
    private var isNew: Boolean? = null
    private var isPopular: Boolean? = null
    private val compositeDisposable = CompositeDisposable()

    @Inject
    lateinit var photoGateway: PhotoGateway
    @Inject
    lateinit var networkGateway: NetworkGateway


    init {
        this.isNew = if (isNew) true else null
        this.isPopular = if (isPopular) true else null
        App.appComponent.inject(this)
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.setAdapter(recyclerAdapter)
        getPhotos()
    }

    fun onResume() {
        if (!networkGateway.isNetworkAvailable()) {
            viewState.showNetworkError()
            compositeDisposable.clear()
        }
    }

    override fun onDestroy() {
        compositeDisposable.clear()
    }

    fun onRecyclerViewScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        val layoutManager = recyclerView.layoutManager as GridLayoutManager
        val lastVisibleItemPos = layoutManager.findLastVisibleItemPosition()

        // if we need to load new content
        if (lastVisibleItemPos + ITEMS_BUFFER >= layoutManager.itemCount - 1) {
            getPhotos()
        }
    }

    fun onRefresh() {
        compositeDisposable?.clear()
        clearPhotos()
        currentPage = 0
        getPhotos()
    }

    fun onPhotoClicked(photo: PhotoModel) {
        viewState.openPhoto(photo)
    }

    fun onHighlightPhoto(photo: PhotoModel) {
        viewState.showLoadingDialog()

        compositeDisposable.clear()
        isGetPhotosRequestSent = false
        currentPage = 0

        var isLoaded = false

        val data = ArrayList<PhotoModel>()
        var totalItems= -1

            // TODO Fix this place
        Single.defer {
            photoGateway.getPhotos(++currentPage, ITEMS_REQUEST_SIZE, isNew, isPopular)
                .doOnSuccess {
                    totalItems = it.totalItems
                    data.addAll(it.data)
                }
                .doOnSuccess {
                    val index = data.indexOfFirst { it.id == photo.id }
                    if (index != -1) {
                        isLoaded = true
                    }
                }
        }
            .repeatUntil {
                (data.size >= totalItems && totalItems != -1) || isLoaded
            }
            .map { data }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ photos ->
                clearPhotos()
                addPhotos(data)
                viewState.hideLoadingDialog()
                highlightPhoto(photo.id)
            }, {
                it.printStackTrace()
            })
            .let(compositeDisposable::add)
    }

    private fun addPhoto(photo: PhotoModel)
    {
        recyclerAdapter.addPhotoModel(photo)
    }

    private fun addPhotos(photos: List<PhotoModel>)
    {
        recyclerAdapter.addPhotoModels(photos)
    }

    fun highlightPhoto(id: Int)
    {
        recyclerAdapter.highlightPhoto(id)
    }

    fun clearPhotos()
    {
        recyclerAdapter.clearPhotoModels()
    }


    private fun getPhotos() {
        if (isGetPhotosRequestSent) {
            return
        }

        photoGateway.getPhotos(++currentPage, ITEMS_REQUEST_SIZE, new = isNew, popular = isPopular)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<PhotosModel> {
                override fun onSuccess(t: PhotosModel) {
                    addPhotos(t.data)
                    viewState.hideNetworkError()
                    viewState.stopRefreshing()
                    viewState.hideProgress()
                    isGetPhotosRequestSent = false
                }

                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                    isGetPhotosRequestSent = true
                    viewState.showProgress()
                }

                override fun onError(e: Throwable) {
                    viewState.showNetworkError()
                    isGetPhotosRequestSent = false
                }
            })
    }


    companion object {
        const val PRESENTER_TAG = "photos_presenter"
        private const val ITEMS_REQUEST_SIZE: Int = 6
        private const val ITEMS_BUFFER: Int = 4
    }
}
