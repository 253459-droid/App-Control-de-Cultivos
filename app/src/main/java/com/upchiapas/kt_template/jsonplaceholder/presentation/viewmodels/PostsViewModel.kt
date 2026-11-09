package com.upchiapas.kt_template.jsonplaceholder.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.upchiapas.kt_template.jsonplaceholder.data.repository.PostsRepository
import com.upchiapas.kt_template.jsonplaceholder.presentation.screens.PostsUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PostsViewModel(
    private val repository: PostsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(PostsUiState())
    val uiState: StateFlow<PostsUiState> = _uiState.asStateFlow()

    init {
        fetchPosts()
    }

    fun fetchPosts() {
        _uiState.update { it.copy(isLoading = true, errorMessage = null) }
        viewModelScope.launch {
            val result = repository.getPosts()
            result.fold(
                onSuccess = { posts ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            posts = posts
                        )
                    }
                },
                onFailure = { error ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = error.localizedMessage ?: "Error al cargar publicaciones"
                        )
                    }
                }
            )
        }
    }

    fun openCreateDialog() {
        _uiState.update { it.copy(showCreateDialog = true) }
    }

    fun closeCreateDialog() {
        _uiState.update { it.copy(showCreateDialog = false) }
    }

    fun createPost(title: String, body: String) {
        _uiState.update { it.copy(isCreating = true, showCreateDialog = false) }
        viewModelScope.launch {
            val result = repository.createPost(title, body)
            result.fold(
                onSuccess = { newPost ->
                    _uiState.update { state ->
                        state.copy(
                            isCreating = false,
                            posts = listOf(newPost) + state.posts,
                            userMessage = "¡Publicación creada exitosamente (ID: ${newPost.id})!"
                        )
                    }
                },
                onFailure = { error ->
                    _uiState.update {
                        it.copy(
                            isCreating = false,
                            userMessage = "Error al crear la publicación: ${error.localizedMessage}"
                        )
                    }
                }
            )
        }
    }

    fun clearUserMessage() {
        _uiState.update { it.copy(userMessage = null) }
    }
}
