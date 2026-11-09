package com.upchiapas.kt_template.auth.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.upchiapas.kt_template.KtApplication
import com.upchiapas.kt_template.auth.domain.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel(private val authRepository: AuthRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<AuthUiState>(AuthUiState.Idle)
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    fun login(email: String, contrasena: String) {
        if (email.isBlank() || contrasena.isBlank()) {
            _uiState.value = AuthUiState.Error("Llena todos los campos")
            return
        }

        _uiState.value = AuthUiState.Cargando
        viewModelScope.launch {
            val result = authRepository.login(email, contrasena)
            if (result.isSuccess) {
                _uiState.value = AuthUiState.Exito
            } else {
                _uiState.value = AuthUiState.Error(result.exceptionOrNull()?.message ?: "Error desconocido")
            }
        }
    }

    fun register(nombre: String, email: String, contrasena: String, confirmarContrasena: String) {
        if (nombre.isBlank() || email.isBlank() || contrasena.isBlank() || confirmarContrasena.isBlank()) {
            _uiState.value = AuthUiState.Error("Llena todos los campos")
            return
        }
        if (contrasena.length < 8) {
            _uiState.value = AuthUiState.Error("La contraseña debe tener al menos 8 caracteres")
            return
        }
        if (contrasena != confirmarContrasena) {
            _uiState.value = AuthUiState.Error("Las contraseñas no coinciden")
            return
        }

        _uiState.value = AuthUiState.Cargando
        viewModelScope.launch {
            val result = authRepository.register(nombre, email, contrasena)
            if (result.isSuccess) {
                _uiState.value = AuthUiState.Exito
            } else {
                _uiState.value = AuthUiState.Error(result.exceptionOrNull()?.message ?: "Error al registrar")
            }
        }
    }

    fun clearError() {
        if (_uiState.value is AuthUiState.Error) {
            _uiState.value = AuthUiState.Idle
        }
    }

    fun cerrarSesion() {
        viewModelScope.launch {
            authRepository.logout()
            _uiState.value = AuthUiState.Idle
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as KtApplication)
                val authRepository = application.container.authRepository
                AuthViewModel(authRepository)
            }
        }
    }
}