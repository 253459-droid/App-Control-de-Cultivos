package com.upchiapas.kt_template.cultivos.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class ParcelaDto(
    val id: Int? = null,
    val nombre: String,
    val cultivo: String,
    val hectareas: Double,
    val fechaSiembra: String,
    val tieneRiego: Boolean
)