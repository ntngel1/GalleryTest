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
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@InjectViewState
class PhotosPresenter @Inject constructor(
    private val photoGateway: PhotoGateway,
    private val networkGateway: NetworkGateway
) : MvpPresenter<PhotosView>() {

    var isNew: Boolean? = null
    var isPopular: Boolean? = null
    private lateinit var recyclerAdapter: PhotosAdapter
    /*private val recyclerAdapter: PhotosAdapter = PhotosAdapter().apply {
        onPhotoClickedListener = this@PhotosPresenter::onPhotoClicked
    }*/
    private var currentPage: Int = 0
    private var isRequestSent = false
    private val compositeDisposable = CompositeDisposable()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        recyclerAdapter = App.appComponent.providePhotosAdapter().apply {
            onPhotoClickedListener = this@PhotosPresenter::onPhotoClicked
        }
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
        synchronized(this::getPhotos) {
            compositeDisposable.clear()
            isRequestSent = false
            currentPage = 0
        }

        var isLoaded = false
        var itemsOnServer = -1
        val data = ArrayList<PhotoModel>()

        fun Single<*>.stopWhen(predicate: () -> Boolean): Flowable<*> = Single.defer { this }.repeatUntil(predicate)

        photoGateway.getPhotos(++currentPage, ITEMS_REQUEST_SIZE, isNew, isPopular)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess { photoModel ->
                itemsOnServer = photoModel.totalItems
                data.addAll(photoModel.data)
                val indexOfPhoto = data.indexOfFirst { it.id == photo.id }
                if (indexOfPhoto >= 0) {
                    isLoaded = true
                }
            }
            .stopWhen { (itemsOnServer > data.size && itemsOnServer != -1) || isLoaded }
            .doOnSubscribe { viewState.showLoadingDialog() }
            .doFinally { viewState.hideLoadingDialog() }
            .subscribe({
                clearPhotos()
                addPhotos(data)
                highlightPhoto(photo.id)
            }, { error ->
                error.printStackTrace()
            })
            .let(compositeDisposable::add)
    }

    private fun addPhoto(photo: PhotoModel) {
        recyclerAdapter.addPhotoModel(photo)
    }

    private fun addPhotos(photos: List<PhotoModel>) {
        recyclerAdapter.addPhotoModels(photos)
    }

    fun highlightPhoto(id: Int) {
        recyclerAdapter.highlightPhoto(id)
    }

    fun clearPhotos() {
        recyclerAdapter.clearPhotoModels()
    }

    private fun getPhotos() {
        // TODO как идея вынести этот if в takeIf прямо в цепочку single?
        if (isRequestSent) {
            return
        }

        photoGateway.getPhotos(++currentPage, ITEMS_REQUEST_SIZE, isNew, isPopular)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                viewState.showProgress()
                isRequestSent = true
            }
            .doFinally {
                viewState.hideProgress()
                viewState.stopRefreshing()
                isRequestSent = false
            }
            .subscribe({ photos ->
                addPhotos(photos.data)
                viewState.hideNetworkError()
            }, { error ->
                viewState.showNetworkError()
                error.printStackTrace()
            })
            .let(compositeDisposable::add)
    }


    companion object {
        const val PRESENTER_TAG = "photos_presenter"
        private const val ITEMS_REQUEST_SIZE: Int = 8
        private const val ITEMS_BUFFER: Int = 4
    }
}
