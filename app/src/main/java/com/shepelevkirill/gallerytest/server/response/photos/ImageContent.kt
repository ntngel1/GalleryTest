package com.shepelevkirill.gallerytest.server.response.photos

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ImageContent {
    @Expose
    var id: Int = 0

    @Expose
    var contentUrl: String? = null

    fun getFullUrl(): String {
        return "http://gallery.dev.webant.ru/media/$contentUrl"
    }
}