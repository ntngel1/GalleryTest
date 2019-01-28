package com.shepelevkirill.gallery.server

import com.shepelevkirill.gallery.server.response.photos.GetPhotosResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/*

    Basic API for a server

 */

interface GalleryService {

    @GET("api/photos")
    fun getPhotos(@Query("page") page: Int, @Query("limit") limit: Int,
                  @Query("new") new: Boolean?, @Query("popular") popular: Boolean?): Call<GetPhotosResponse>
}