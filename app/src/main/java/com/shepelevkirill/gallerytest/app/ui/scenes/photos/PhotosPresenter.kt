package com.shepelevkirill.gallerytest.app.ui.scenes.photos

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.shepelevkirill.gallerytest.domain.gateway.NetworkGateway
import com.shepelevkirill.gallerytest.domain.gateway.PhotoGateway
import com.shepelevkirill.gallerytest.domain.models.PhotoModel
import com.shepelevkirill.gallerytest.app.App
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@InjectViewState
class PhotosPresenter(isNew: Boolean, isPopular: Boolean) : MvpPresenter<PhotosView>() {
    @Inject lateinit var photoGateway: PhotoGateway
    @Inject lateinit var networkGateway: NetworkGateway

    private var currentPage: Int = 0
    private var isRequestSent = false

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
            compositeDisposable.dispose()
        }
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
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

    private fun getPhotos() {
        if (isRequestSent)
            return
        // TODO Handle disposable
        photoGateway.getPhotos(++currentPage, ITEMS_REQUEST_SIZE, new = isNew, popular = isPopular)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .flatMapObservable { Observable.fromIterable(it.data) }
            .subscribe(object : Observer<PhotoModel> {
                override fun onComplete() {
                    viewState.hideNetworkError()
                    viewState.stopRefreshing()
                    viewState.hideProgress()
                    isRequestSent = false
                }

                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                    isRequestSent = true
                    viewState.showProgress()
                }

                override fun onNext(t: PhotoModel) {
                    viewState.addPhoto(t)
                }

                override fun onError(e: Throwable) {
                    viewState.showNetworkError()
                    isRequestSent = false
                }

            })
    }
}
