package com.example.organisync.Response

import com.google.gson.annotations.SerializedName

data class ResponseStory(
    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("listStory")
    val listStory: List<Story>
)
data class Story(
    @field:SerializedName("id")
    val id: String?,

    @field:SerializedName("name")
    val name: String?,

    @field:SerializedName("description")
    val description: String?,

    @field:SerializedName("photoUrl")
    val photoUrl: String?,

    @field:SerializedName("createdAt")
    val createdAt: String?,

    @field:SerializedName("lat")
    val lat: String?,

    @field:SerializedName("on")
    val lon: String?
)
