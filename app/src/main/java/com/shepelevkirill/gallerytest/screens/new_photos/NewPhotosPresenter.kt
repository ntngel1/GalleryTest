package com.shepelevkirill.gallerytest.screens.new_photos

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shepelevkirill.core.gateway.PhotoGateway
import com.shepelevkirill.core.models.PhotoModel
import com.shepelevkirill.gallerytest.App
import com.shepelevkirill.gallerytest.core.screens.NewPhotos
import com.shepelevkirill.gateway.network.gateway.PhotoApiGateway
import com.shepelevkirill.gateway.network.retrofit
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class NewPhotosPresenter : NewPhotos.Presenter {
    private var view: NewPhotos.View? = null
    private var photoGateway: PhotoGateway = PhotoApiGateway(retrofit.getRetrofit())
    private var currentPage: Int = 0
    private var isRequestSent = false

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

    override fun onDestroy() {
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

    override fun onOpen() {
        Log.d("ON OPEN", "CHECKING INTERNET")
        val cm = App.appContext!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = cm.activeNetworkInfo
        if (netInfo == null || !netInfo.isConnected) view?.showNetworkError()
    }

    private fun getPhotos() {
        if (isRequestSent)
            return

        photoGateway.getPhotos(++currentPage, ITEMS_REQUEST_SIZE, new = true, popular = null)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .flatMapObservable { Observable.fromIterable(it.data) }
            .subscribe(object : Observer<PhotoModel> {
                override fun onComplete() {
                    view?.hideNetworkError()
                    view?.stopRefreshing()
                    view?.hideProgress()
                    isRequestSent = false
                }

                override fun onSubscribe(d: Disposable) {
                    isRequestSent = true
                    view?.showProgress()
                }

                override fun onNext(t: PhotoModel) {
                    view?.showPhoto(t)
                }

                override fun onError(e: Throwable) {
                    view?.showNetworkError()
                    isRequestSent = false
                }

            })
    }
}