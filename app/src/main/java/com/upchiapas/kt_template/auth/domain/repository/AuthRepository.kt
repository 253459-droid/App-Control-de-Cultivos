package com.upchiapas.kt_template.auth.domain.repository

interface AuthRepository {
    suspend fun login(email: String, contrasena: String): Result<String>
    suspend fun register(nombre: String, email: String, contrasena: String): Result<String>

    suspend fun logout()
}