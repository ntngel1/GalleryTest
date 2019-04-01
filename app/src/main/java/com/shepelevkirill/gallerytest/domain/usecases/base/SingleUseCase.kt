package com.shepelevkirill.gallerytest.domain.usecases.base

import io.reactivex.Scheduler
import io.reactivex.Single

abstract class SingleUseCase <in P, T> : UseCase<P, Single<T>>() {
    private var executionScheduler: Scheduler? = null
    private var postExecutionScheduler: Scheduler? = null

    fun setSchedulers(executionScheduler: Scheduler, postExecutionScheduler: Scheduler): SingleUseCase<P, T> {
        this.executionScheduler = executionScheduler
        this.postExecutionScheduler = postExecutionScheduler
        return this
    }

    protected abstract fun build(param: P): Single<T>

    override fun execute(param: P): Single<T> {
        var single = build(param)

        if (executionScheduler != null) {
            single = single.subscribeOn(executionScheduler)
        }

        if (postExecutionScheduler != null) {
            single = single.observeOn(postExecutionScheduler)
        }

        return single
    }
}