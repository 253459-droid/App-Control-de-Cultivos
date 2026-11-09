package com.upchiapas.kt_template.jsonplaceholder.di

import com.upchiapas.kt_template.jsonplaceholder.data.datasource.PostsApiService
import com.upchiapas.kt_template.jsonplaceholder.data.repository.PostsRepository
import com.upchiapas.kt_template.jsonplaceholder.data.repository.PostsRepositoryImpl
import com.upchiapas.kt_template.jsonplaceholder.presentation.viewmodels.PostsViewModelFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class JsonPlaceholderModule {

    private val baseUrl = "https://jsonplaceholder.typicode.com/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val apiService: PostsApiService by lazy {
        retrofit.create(PostsApiService::class.java)
    }

    val repository: PostsRepository by lazy {
        PostsRepositoryImpl(apiService)
    }

    val factoryPosts: PostsViewModelFactory by lazy {
        PostsViewModelFactory(repository)
    }
}
