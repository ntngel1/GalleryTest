package com.shepelevkirill.gallerytest.server.response.photos

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Image {
    @SerializedName("id")
    @Expose
    var id: Int = 0

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("description")
    @Expose
    var description: String? = null

    @SerializedName("new")
    @Expose
    var isNew: Boolean = false

    @SerializedName("popular")
    @Expose
    var isPopular: Boolean = false

    @SerializedName("image")
    @Expose
    var image: ImageContent? = null
}