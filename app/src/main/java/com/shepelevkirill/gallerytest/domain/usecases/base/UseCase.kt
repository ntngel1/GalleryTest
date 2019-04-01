package com.shepelevkirill.gallerytest.domain.usecases.base

import io.reactivex.Completable
import io.reactivex.Scheduler
import io.reactivex.Single

abstract class UseCase <in P, out T> {
    abstract fun execute(param: P): T
}