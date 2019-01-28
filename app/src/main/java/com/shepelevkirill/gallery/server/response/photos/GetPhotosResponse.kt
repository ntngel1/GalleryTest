package com.shepelevkirill.gallery.server.response.photos

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/*

    GET("/api/photos/") response class

 */

class GetPhotosResponse {
    @SerializedName("totalItems")
    @Expose
    var totalItems: Int = 0

    @SerializedName("itemsPerPage")
    @Expose
    var itemsPerPage: Int = 0

    @SerializedName("countOfPages")
    @Expose
    var countOfPages: Int = 0

    @SerializedName("data")
    @Expose
    var images: List<Image>? = null
}