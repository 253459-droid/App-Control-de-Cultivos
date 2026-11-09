package com.upchiapas.kt_template.auth.presentation.viewmodels

sealed interface AuthUiState {
    object Idle : AuthUiState
    object Cargando : AuthUiState
    object Exito : AuthUiState
    data class Error(val mensaje: String) : AuthUiState
}