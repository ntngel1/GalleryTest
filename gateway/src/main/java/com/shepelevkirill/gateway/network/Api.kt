package com.shepelevkirill.gateway.network

import com.shepelevkirill.core.models.*
import io.reactivex.Single
import retrofit2.http.*

interface Api {

    // CLIENT

    @GET("/api/clients")
    fun getClients(page: Int, limit: Int): Single<List<ClientModel>>

    @POST("/api/clients")
    fun createClient(@Body client: ClientCreateRequestModel): Single<List<ClientModel>>

    @GET("/api/clients/{id}")
    fun getClient(@Path("id") id: Int): Single<ClientModel>

    @PUT("/api/clients/{id}")
    fun updateClient(@Path("id") id: Int, @Body client: ClientUpdateRequestModel): Single<ClientModel>

    @PATCH("/api/clients/{id}")
    fun replaceClient(@Path("id") id: Int, @Body client: ClientReplaceRequestModel): Single<ClientModel>

    @DELETE("/api/clients/{id}")
    fun removeClient(@Path("id") id: Int): Single<Any>



    // MEDIA OBJECT

    @GET("/api/media_objects")
    fun getMediaObjects(page: Int, limit: Int): Single<List<MediaObjectModel>>

    @POST("/api/media_objects")
    fun createMediaObject(@Body mediaObject: MediaObjectCreateRequestModel): Single<MediaObjectModel>

    @GET("/api/media_objects/{id}")
    fun getMediaObject(@Path("id") id: Int): Single<MediaObjectModel>

    @PUT("/api/media_objects/{id}")
    fun updateMediaObject(@Path("id") id: Int, @Body mediaObject: MediaObjectUpdateRequestModel): Single<MediaObjectModel>

    @PATCH("/api/media_objects/{id}")
    fun replaceMediaObject(@Path("id") id: Int, @Body mediaObject: MediaObjectReplaceRequestModel): Single<MediaObjectModel>

    @DELETE("/api/media_objects/{id}")
    fun removeMediaObject(@Path("id") id: Int): Single<Any>




    // PHOTO

    @GET("/api/photos")
    fun getPhotos(@Query("page") page: Int, @Query("limit") limit: Int, @Query("new") new: Boolean?, @Query("popular") popular: Boolean?): Single<PhotosModel>

    @POST("/api/photos")
    fun createPhoto(@Body photo: PhotoCreateRequestModel): Single<PhotoModel>

    @GET("/api/photos/{id}")
    fun getPhoto(@Path("id") id: Int): Single<PhotoModel>

    @PUT("/api/photos/{id}")
    fun updatePhoto(@Path("id") id: Int, @Body photo: PhotoUpdateRequestModel): Single<PhotoModel>

    @PATCH("/photos/{id}")
    fun replacePhoto(@Path("id") id: Int, @Body photo: PhotoReplaceRequestModel): Single<PhotoModel>

    @DELETE("/api/photos/{id}")
    fun removePhoto(@Path("id") id: Int): Single<Any>



    // USER
    @GET("/api/users")
    fun getUsers(page: Int, limit: Int): Single<List<UserModel>>

    @POST("/api/users")
    fun createUser(@Body photo: UserCreateRequestModel): Single<UserModel>

    @GET("/api/users/{id}")
    fun getUser(@Path("id") id: Int): Single<UserModel>

    @PUT("/api/users/{id}")
    fun updateUser(@Path("id") id: Int, @Body photo: UserUpdateRequestModel): Single<UserModel>

    @DELETE("/api/users/{id}")
    fun removeUser(@Path("id") id: Int): Single<Any>

}