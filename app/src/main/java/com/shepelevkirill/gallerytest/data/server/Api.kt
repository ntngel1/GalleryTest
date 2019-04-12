package com.shepelevkirill.server

import com.shepelevkirill.gallerytest.domain.models.*
import io.reactivex.Single
import okhttp3.MultipartBody
import retrofit2.http.*

interface Api {

    @POST("/api/clients")
    fun createClient(@Body client: ClientCreateRequestModel): Single<ClientModel>

    @Multipart
    @POST("/api/media_objects")
    fun uploadPhoto(@Part file: MultipartBody.Part): Single<MediaObjectModel>

    @GET("/api/photos")
    fun getPhotos(@Query("page") page: Int,
                  @Query("limit") limit: Int,
                  @Query("new") new: Boolean?,
                  @Query("popular") popular: Boolean?,
                  @Query("order[id]") orderId: Int = 0): Single<PhotosModel>

    @POST("/api/photos")
    fun createPhoto(@Body photo: PhotoCreateRequestModel): Single<PhotoModel>

    @GET("/api/photos/{id}")
    fun getPhoto(@Path("id") id: Int): Single<PhotoModel>

    @GET("/api/users/current")
    fun getCurrentUser(): Single<UserModel>

    @FormUrlEncoded
    @POST("/oauth/v2/token")
    fun getTokenByUser(@Field("username") username: String,
                       @Field("password") password: String,
                       @Field("client_id") clientId: String,
                       @Field("client_secret") clientSecret: String,
                       @Field("grant_type") grantType: String = "password"): Single<TokenModel>

    @FormUrlEncoded
    @POST("/oauth/v2/token")
    fun getTokenByRefreshToken(@Field("refresh_token") refreshToken: String,
                               @Field("client_id") clientId: String,
                               @Field("client_secret") clientSecret: String,
                               @Field("grant_type") grantType: String = "refresh_token"): Single<TokenModel>
}