package com.shepelevkirill.gallerytest.server.response.photos

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Image {
    @Expose
    var id: Int = 0

    @Expose
    var name: String? = null

    @Expose
    var description: String? = null

    @SerializedName("new")
    @Expose
    var isNew: Boolean = false

    @SerializedName("popular")
    @Expose
    var isPopular: Boolean = false

    @Expose
    var image: ImageContent? = null
}