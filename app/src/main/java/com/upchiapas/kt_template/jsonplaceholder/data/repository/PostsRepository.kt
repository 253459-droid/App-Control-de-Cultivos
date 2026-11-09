package com.upchiapas.kt_template.jsonplaceholder.data.repository

import com.upchiapas.kt_template.jsonplaceholder.data.model.Post

interface PostsRepository {
    suspend fun getPosts(): Result<List<Post>>
    suspend fun createPost(title: String, body: String): Result<Post>
}
