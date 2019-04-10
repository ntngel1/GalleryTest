package com.shepelevkirill.gallerytest.domain.usecases.base

import io.reactivex.Observable
import io.reactivex.Scheduler

abstract class ObservableUseCase <in P, T> : UseCase<P, Observable<T>>() {
    private lateinit var executionScheduler: Scheduler
    private lateinit var postExecutionScheduler: Scheduler

    fun setSchedulers(executionScheduler: Scheduler, postExecutionScheduler: Scheduler): ObservableUseCase<P, T> {
        this.executionScheduler = executionScheduler
        this.postExecutionScheduler = postExecutionScheduler
        return this
    }

    protected abstract fun build(param: P): Observable<T>

    override fun execute(param: P): Observable<T> {
        return build(param).subscribeOn(executionScheduler)
            .observeOn(postExecutionScheduler)
    }
}