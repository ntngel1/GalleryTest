package com.shepelevkirill.gallerytest.app.ui.scenes.photos

import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.shepelevkirill.gallerytest.domain.gateway.NetworkGateway
import com.shepelevkirill.gallerytest.domain.gateway.PhotoGateway
import com.shepelevkirill.gallerytest.domain.models.PhotoModel
import com.shepelevkirill.gallerytest.app.App
import com.shepelevkirill.gallerytest.domain.models.PhotosModel
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@InjectViewState
class PhotosPresenter(isNew: Boolean, isPopular: Boolean) : MvpPresenter<PhotosView>() {
    @Inject
    lateinit var photoGateway: PhotoGateway
    @Inject
    lateinit var networkGateway: NetworkGateway

    private var currentPage: Int = 0
    private var isGetPhotosRequestSent = false

    private var isNew: Boolean? = null
    private var isPopular: Boolean? = null

    private val compositeDisposable = CompositeDisposable()

    init {
        this.isNew = if (isNew) true else null
        this.isPopular = if (isPopular) true else null
        App.appComponent.inject(this)
    }

    companion object {
        private const val ITEMS_REQUEST_SIZE: Int = 6
        private const val ITEMS_BUFFER: Int = 4
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
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
        viewState.clearPhotos()
        currentPage = 0
        getPhotos()
    }

    fun onPhotoClicked(photo: PhotoModel) {
        viewState.openPhoto(photo)
    }

    fun onHighlightPhoto(photo: PhotoModel) {
        compositeDisposable.clear()
        isGetPhotosRequestSent = false
        currentPage = 0

        var isLoaded = false

        val data = ArrayList<PhotoModel>()
        var totalItems= -1

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
                viewState.clearPhotos()
                viewState.addPhotos(data)
                val index = data.indexOfFirst { it.id == photo.id }
                viewState.highlightPhotoWithIndex(index)
            }, {
                it.printStackTrace()
            })
            .let(compositeDisposable::add)
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
                    viewState.addPhotos(t.data)
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
                /*override fun onComplete() {
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

                override fun onNext(t: PhotosModel) {
                    viewState.addPhotos(t.data)
                }

                override fun onError(e: Throwable) {
                    viewState.showNetworkError()
                    isGetPhotosRequestSent = false
                }*/
            })
    }
}
