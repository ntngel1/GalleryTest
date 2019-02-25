package com.shepelevkirill.gallerytest.screens.new_photos

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shepelevkirill.core.gateway.PhotoGateway
import com.shepelevkirill.core.models.PhotoModel
import com.shepelevkirill.gallerytest.core.screens.NewPhotos
import com.shepelevkirill.gateway.network.gateway.PhotoApiGateway
import com.shepelevkirill.gateway.network.retrofit
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class NewPhotosPresenter : NewPhotos.Presenter {
    private var view: NewPhotos.View? = null
    private var photoGateway: PhotoGateway = PhotoApiGateway(retrofit.getRetrofit())
    private var currentPage: Int = 0

    companion object {
        private const val ITEMS_REQUEST_SIZE: Int = 6
        private const val ITEMS_BUFFER: Int = 4
    }

    override fun attachView(view: NewPhotos.View) {
        this.view = view
    }

    override fun detachView() {
        view = null
    }

    override fun onCreate() {
        getPhotos()
    }

    override fun onRecyclerViewScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        val layoutManager = recyclerView.layoutManager as GridLayoutManager
        val lastVisibleItemPos = layoutManager.findLastVisibleItemPosition()

        // if we need to load new content
        if (lastVisibleItemPos + ITEMS_BUFFER >= layoutManager.itemCount - 1) {
            getPhotos()
        }
    }

    override fun onRefresh() {
        view?.clearPhotos()
        currentPage = 0
        getPhotos()
    }

    override fun onPhotoClicked(photo: PhotoModel) {
        view!!.openPhoto(photo)
    }

    private fun getPhotos() {
        photoGateway.getPhotos(++currentPage, ITEMS_REQUEST_SIZE, new = true, popular = null)
            .doOnError { it.printStackTrace() }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .flatMapObservable { Observable.fromIterable(it.data) }
            .doOnError { view?.showNetworkError() }
            .doFinally { view?.stopRefreshing() }
            .subscribe {
                view?.showPhoto(it)
            }
    }
}