package com.upchiapas.kt_template.auth.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val email: String,
    val contrasena: String
)