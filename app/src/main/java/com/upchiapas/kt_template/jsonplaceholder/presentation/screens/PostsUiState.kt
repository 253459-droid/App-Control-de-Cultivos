package com.upchiapas.kt_template.jsonplaceholder.presentation.screens

import com.upchiapas.kt_template.jsonplaceholder.data.model.Post

data class PostsUiState(
    val isLoading: Boolean = false,
    val posts: List<Post> = emptyList(),
    val errorMessage: String? = null,
    val isCreating: Boolean = false,
    val showCreateDialog: Boolean = false,
    val userMessage: String? = null
)
