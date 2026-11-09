package com.upchiapas.kt_template.jsonplaceholder.data.repository

import com.upchiapas.kt_template.jsonplaceholder.data.datasource.PostsApiService
import com.upchiapas.kt_template.jsonplaceholder.data.dto.CreatePostDto
import com.upchiapas.kt_template.jsonplaceholder.data.mapper.toDomain
import com.upchiapas.kt_template.jsonplaceholder.data.mapper.toDomainList
import com.upchiapas.kt_template.jsonplaceholder.data.model.Post
import retrofit2.HttpException
import java.io.IOException

class PostsRepositoryImpl(
    private val apiService: PostsApiService
) : PostsRepository {

    override suspend fun getPosts(): Result<List<Post>> {
        return runCatching {
            apiService.getPosts().toDomainList()
        }.mapFailure { throwable ->
            mapThrowable(throwable)
        }
    }

    override suspend fun createPost(title: String, body: String): Result<Post> {
        return runCatching {
            val createDto = CreatePostDto(userId = 1, title = title, body = body)
            apiService.createPost(createDto).toDomain()
        }.mapFailure { throwable ->
            mapThrowable(throwable)
        }
    }

    private fun <T> Result<T>.mapFailure(transform: (Throwable) -> Throwable): Result<T> {
        return fold(
            onSuccess = { Result.success(it) },
            onFailure = { Result.failure(transform(it)) }
        )
    }

    private fun mapThrowable(throwable: Throwable): Throwable {
        return when (throwable) {
            is HttpException -> Exception("Error HTTP ${throwable.code()}: ${throwable.message()}")
            is IOException -> Exception("Error de red: Comprueba tu conexión a internet")
            else -> throwable
        }
    }
}
