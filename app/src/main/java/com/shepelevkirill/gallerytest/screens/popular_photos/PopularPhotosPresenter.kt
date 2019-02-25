package com.shepelevkirill.gallerytest.screens.popular_photos

import androidx.recyclerview.widget.RecyclerView
import com.shepelevkirill.core.gateway.PhotoGateway
import com.shepelevkirill.core.models.PhotoModel
import com.shepelevkirill.gallerytest.core.screens.PopularPhotos
import com.shepelevkirill.gateway.network.gateway.PhotoApiGateway
import com.shepelevkirill.gateway.network.retrofit
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class PopularPhotosPresenter : PopularPhotos.Presenter {
    private var view: PopularPhotos.View? = null
    private var photoGateway: PhotoGateway = PhotoApiGateway(retrofit.getRetrofit())
    private var currentPage: Int = 0

    override fun attachView(view: PopularPhotos.View) {
        this.view = view
    }

    override fun detachView() {
        view = null
    }

    override fun onCreate() {
        getPhotos()
    }

    override fun onRecyclerViewScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        getPhotos()
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
        photoGateway.getPhotos(++currentPage, 4, new = null, popular = true)
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