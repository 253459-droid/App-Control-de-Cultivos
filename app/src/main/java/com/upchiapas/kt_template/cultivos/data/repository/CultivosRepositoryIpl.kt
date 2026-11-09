package com.upchiapas.kt_template.cultivos.data.repository

import com.upchiapas.kt_template.cultivos.data.datasource.CultivosApiService
import com.upchiapas.kt_template.cultivos.data.mapper.toDomain
import com.upchiapas.kt_template.cultivos.data.mapper.toDto
import com.upchiapas.kt_template.cultivos.domain.model.Parcela
import com.upchiapas.kt_template.cultivos.domain.repository.CultivosRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CultivosRepositoryImpl(
    private val apiService: CultivosApiService
) : CultivosRepository {

    override suspend fun getParcelas(): Result<List<Parcela>> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.getParcelas()
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!.map { it.toDomain() })
            } else {
                Result.failure(Exception("Error al cargar las parcelas"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getParcelaById(id: Int): Result<Parcela> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.getParcelaById(id)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!.toDomain())
            } else {
                Result.failure(Exception("Error al obtener la parcela"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun createParcela(parcela: Parcela): Result<Parcela> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.createParcela(parcela.toDto())
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!.toDomain())
            } else {
                Result.failure(Exception("Error al crear la parcela"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateParcela(id: Int, parcela: Parcela): Result<Parcela> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.updateParcela(id, parcela.toDto())
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!.toDomain())
            } else {
                Result.failure(Exception("Error al actualizar la parcela"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteParcela(id: Int): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.deleteParcela(id)
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Error al eliminar la parcela"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}