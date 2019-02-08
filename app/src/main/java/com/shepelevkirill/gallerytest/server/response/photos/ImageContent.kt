package com.shepelevkirill.gallerytest.server.response.photos

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ImageContent {
    @SerializedName("id")
    @Expose
    var id: Int = 0

    @SerializedName("contentUrl")
    @Expose
    var contentUrl: String? = null

    fun getFullUrl(): String {
        return "http://gallery.dev.webant.ru/media/$contentUrl"
    }
}