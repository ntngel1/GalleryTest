package com.shepelevkirill.gallerytest.domain.usecases.base

import io.reactivex.Completable
import io.reactivex.Scheduler

abstract class CompletableUseCase <in P> : UseCase<P, Completable>() {
    private var executionScheduler: Scheduler? = null
    private var postExecutionScheduler: Scheduler? = null

    fun setSchedulers(executionScheduler: Scheduler, postExecutionScheduler: Scheduler): CompletableUseCase<P> {
        this.executionScheduler = executionScheduler
        this.postExecutionScheduler = postExecutionScheduler
        return this
    }

    protected abstract fun build(param: P): Completable

    override fun execute(param: P): Completable {
        var completable = build(param)

        if (executionScheduler != null) {
            completable = completable.subscribeOn(executionScheduler)
        }

        if (postExecutionScheduler != null) {
            completable = completable.observeOn(postExecutionScheduler)
        }

        return completable
    }
}

