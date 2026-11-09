package com.upchiapas.kt_template.jsonplaceholder.data.dto

import com.google.gson.annotations.SerializedName

data class CreatePostDto(
    @SerializedName("userId") val userId: Int = 1,
    @SerializedName("title") val title: String,
    @SerializedName("body") val body: String
)
