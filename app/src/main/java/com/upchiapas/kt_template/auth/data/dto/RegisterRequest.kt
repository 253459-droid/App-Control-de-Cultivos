package com.upchiapas.kt_template.auth.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequest(
    val nombre: String,
    val email: String,
    val contrasena: String
)