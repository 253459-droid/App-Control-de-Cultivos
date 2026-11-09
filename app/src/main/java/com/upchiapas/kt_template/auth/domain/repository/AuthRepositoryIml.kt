package com.upchiapas.kt_template.auth.data.repository

import com.upchiapas.kt_template.auth.data.datasource.AuthApiService
import com.upchiapas.kt_template.auth.data.dto.LoginRequest
import com.upchiapas.kt_template.auth.data.dto.RegisterRequest
import com.upchiapas.kt_template.auth.domain.repository.AuthRepository
import com.upchiapas.kt_template.core.session.SessionManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthRepositoryImpl(
    private val apiService: AuthApiService,
    private val sessionManager: SessionManager
) : AuthRepository {

    override suspend fun login(email: String, contrasena: String): Result<String> = withContext(Dispatchers.IO) {
        try {
            val request = LoginRequest(email, contrasena)
            val response = apiService.login(request)

            if (response.isSuccessful && response.body() != null) {
                val token = response.body()!!.token
                sessionManager.saveAuthToken(token)
                Result.success(token)
            } else {
                Result.failure(Exception("Credenciales inválidas o error de red"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun register(nombre: String, email: String, contrasena: String): Result<String> = withContext(Dispatchers.IO) {
        try {
            val request = RegisterRequest(nombre, email, contrasena)
            val response = apiService.register(request)

            if (response.isSuccessful && response.body() != null) {
                val token = response.body()!!.token
                sessionManager.saveAuthToken(token)
                Result.success(token)
            } else {
                Result.failure(Exception("Error al registrar la cuenta"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    override suspend fun logout() {
        sessionManager.clearSession()
    }
}