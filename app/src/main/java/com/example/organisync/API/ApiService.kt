package com.example.organisync.API

import com.example.organisync.Response.ResponseLogin
import com.example.organisync.Response.ResponseRegister
import com.example.organisync.Response.ResponseStory
import com.example.organisync.Response.UploadFileResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {
    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<ResponseLogin>

    @FormUrlEncoded
    @POST("register")
    fun register(
        @Field("name") name : String?,
        @Field("email") email : String?,
        @Field("password") password : String?
    ): Call<ResponseRegister>

    @GET("stories")
    fun getStory(
        @Header("Authorization") auth : String
    ): Call<ResponseStory>

    @Multipart
    @POST("stories")
    fun postStory(
        @Header("Authorization") auth : String,
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody
    ): Call<UploadFileResponse>
}