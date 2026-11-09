package com.upchiapas.kt_template.cultivos.domain.repository

import com.upchiapas.kt_template.cultivos.domain.model.Parcela

interface CultivosRepository {
    suspend fun getParcelas(): Result<List<Parcela>>
    suspend fun getParcelaById(id: Int): Result<Parcela>
    suspend fun createParcela(parcela: Parcela): Result<Parcela>
    suspend fun updateParcela(id: Int, parcela: Parcela): Result<Parcela>
    suspend fun deleteParcela(id: Int): Result<Unit>
}