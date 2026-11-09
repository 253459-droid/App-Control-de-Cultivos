package com.upchiapas.kt_template.cultivos.data.datasource

import com.upchiapas.kt_template.cultivos.data.dto.ParcelaDto
import retrofit2.Response
import retrofit2.http.*

interface CultivosApiService {

    @GET("parcelas")
    suspend fun getParcelas(): Response<List<ParcelaDto>>

    @GET("parcelas/{id}")
    suspend fun getParcelaById(@Path("id") id: Int): Response<ParcelaDto>

    @POST("parcelas")
    suspend fun createParcela(@Body parcela: ParcelaDto): Response<ParcelaDto>

    @PUT("parcelas/{id}")
    suspend fun updateParcela(@Path("id") id: Int, @Body parcela: ParcelaDto): Response<ParcelaDto>

    @DELETE("parcelas/{id}")
    suspend fun deleteParcela(@Path("id") id: Int): Response<Unit>
}