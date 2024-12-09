package com.example.organisync.Response

import com.google.gson.annotations.SerializedName

data class ResponseLogin(
    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String?,

    @field:SerializedName("loginResult")
    val loginResult: login
)

data class login(
    @field:SerializedName("userId")
    val userId: String?,

    @field:SerializedName("name")
    val name: String?,

    @field:SerializedName("token")
    val token: String?
)
