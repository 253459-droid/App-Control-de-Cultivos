package com.upchiapas.kt_template.jsonplaceholder.data.dto

import com.google.gson.annotations.SerializedName

data class PostDto(
    @SerializedName("id") val id: Int? = null,
    @SerializedName("userId") val userId: Int? = null,
    @SerializedName("title") val title: String? = null,
    @SerializedName("body") val body: String? = null
)
