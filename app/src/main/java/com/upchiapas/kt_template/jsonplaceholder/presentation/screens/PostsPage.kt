package com.upchiapas.kt_template.jsonplaceholder.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.upchiapas.kt_template.KtApplication
import com.upchiapas.kt_template.jsonplaceholder.presentation.components.CreatePostDialog
import com.upchiapas.kt_template.jsonplaceholder.presentation.components.PostItem
import com.upchiapas.kt_template.jsonplaceholder.presentation.viewmodels.PostsViewModel

/**
 * Stateful Composable: Se conecta con la arquitectura y el ciclo de vida del ViewModel.
 */
@Composable
fun PostsPage(
    viewModel: PostsViewModel = viewModel(
        factory = (LocalContext.current.applicationContext as KtApplication).jsonPlaceholderModule.factoryPosts
    )
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    PostsContent(
        uiState = uiState,
        onRefresh = { viewModel.fetchPosts() },
        onOpenCreateDialog = { viewModel.openCreateDialog() },
        onCloseCreateDialog = { viewModel.closeCreateDialog() },
        onCreatePost = { title, body -> viewModel.createPost(title, body) },
        onClearUserMessage = { viewModel.clearUserMessage() }
    )
}

/**
 * Stateless Composable: Es la vista pura. Libre de dependencias del ViewModel.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostsContent(
    uiState: PostsUiState,
    onRefresh: () -> Unit,
    onOpenCreateDialog: () -> Unit,
    onCloseCreateDialog: () -> Unit,
    onCreatePost: (title: String, body: String) -> Unit,
    onClearUserMessage: () -> Unit,
    modifier: Modifier = Modifier
) {
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState.userMessage) {
        uiState.userMessage?.let { message ->
            snackbarHostState.showSnackbar(message)
            onClearUserMessage()
        }
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text("Posts - JSONPlaceholder API") },
                actions = {
                    IconButton(onClick = onRefresh) {
                        Icon(imageVector = Icons.Filled.Refresh, contentDescription = "Actualizar")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onOpenCreateDialog) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "Crear publicación")
            }
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            when {
                uiState.isLoading -> {
                    CircularProgressIndicator()
                }

                uiState.errorMessage != null -> {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = uiState.errorMessage,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = onRefresh) {
                            Text("Reintentar")
                        }
                    }
                }

                uiState.posts.isEmpty() -> {
                    Text(
                        text = "No hay publicaciones disponibles.",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(uiState.posts, key = { it.id }) { post ->
                            PostItem(post = post)
                        }
                    }
                }
            }

            if (uiState.showCreateDialog) {
                CreatePostDialog(
                    onDismiss = onCloseCreateDialog,
                    onCreate = onCreatePost
                )
            }

            if (uiState.isCreating) {
                CircularProgressIndicator()
            }
        }
    }
}