package com.upchiapas.kt_template.auth.data.datasource

import com.upchiapas.kt_template.auth.data.dto.AuthResponse
import com.upchiapas.kt_template.auth.data.dto.LoginRequest
import com.upchiapas.kt_template.auth.data.dto.RegisterRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {

    @POST("users")
    suspend fun register(@Body request: RegisterRequest): Response<AuthResponse>

    @POST("login")
    suspend fun login(@Body request: LoginRequest): Response<AuthResponse>
}