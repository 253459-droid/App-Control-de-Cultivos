package com.upchiapas.kt_template.jsonplaceholder.data.mapper

import com.upchiapas.kt_template.jsonplaceholder.data.dto.CreatePostDto
import com.upchiapas.kt_template.jsonplaceholder.data.dto.PostDto
import com.upchiapas.kt_template.jsonplaceholder.data.model.Post

fun PostDto.toDomain(): Post {
    return Post(
        id = id ?: 0,
        userId = userId ?: 0,
        title = title.orEmpty(),
        body = body.orEmpty()
    )
}

fun List<PostDto>.toDomainList(): List<Post> {
    return map { it.toDomain() }
}

fun Post.toCreateDto(): CreatePostDto {
    return CreatePostDto(
        userId = userId,
        title = title,
        body = body
    )
}
