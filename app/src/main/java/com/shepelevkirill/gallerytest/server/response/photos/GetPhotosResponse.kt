package com.shepelevkirill.gallerytest.server.response.photos

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/*

    GET("/api/photos/") response class

 */

class GetPhotosResponse {
    @Expose
    var totalItems: Int = 0

    @Expose
    var itemsPerPage: Int = 0

    @Expose
    var countOfPages: Int = 0

    @SerializedName("data")
    @Expose
    var images: List<Image>? = null
}