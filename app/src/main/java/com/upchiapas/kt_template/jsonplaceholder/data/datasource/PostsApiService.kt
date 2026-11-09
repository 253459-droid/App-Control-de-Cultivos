package com.upchiapas.kt_template.jsonplaceholder.data.datasource

import com.upchiapas.kt_template.jsonplaceholder.data.dto.CreatePostDto
import com.upchiapas.kt_template.jsonplaceholder.data.dto.PostDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface PostsApiService {

    @GET("posts")
    suspend fun getPosts(): List<PostDto>

    @POST("posts")
    suspend fun createPost(@Body post: CreatePostDto): PostDto
}
