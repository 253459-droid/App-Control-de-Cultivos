package com.upchiapas.kt_template.auth.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
    val token: String = "token_de_prueba"
)