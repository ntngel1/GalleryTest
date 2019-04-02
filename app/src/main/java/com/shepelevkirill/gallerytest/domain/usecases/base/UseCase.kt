package com.shepelevkirill.gallerytest.domain.usecases.base

abstract class UseCase <in P, out T> {
    abstract fun execute(param: P): T
}