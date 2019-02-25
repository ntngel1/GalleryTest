package com.shepelevkirill.gallerytest.screens.new_photos

import androidx.recyclerview.widget.RecyclerView
import com.shepelevkirill.core.gateway.PhotoGateway
import com.shepelevkirill.core.models.PhotoModel
import com.shepelevkirill.gallerytest.core.screens.NewPhotos
import com.shepelevkirill.gateway.network.gateway.PhotoApiGateway
import com.shepelevkirill.gateway.network.retrofit
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class NewPhotosPresenter() : NewPhotos.Presenter {
    private var view: NewPhotos.View? = null
    private var photoGateway: PhotoGateway = PhotoApiGateway(retrofit.getRetrofit())
    private var currentPage: Int = 0

    override fun onRecyclerViewScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        getPhotos()
    }

    override fun onRefresh() {
        view?.clearPhotos()
        currentPage = 0
        getPhotos()
    }

    override fun attachView(view: NewPhotos.View) {
        this.view = view
    }

    override fun detachView() {
        view = null
    }

    override fun onPhotoClicked(photo: PhotoModel) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun getPhotos() {
        photoGateway.getPhotos(++currentPage, 4, new = true, popular = false)
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