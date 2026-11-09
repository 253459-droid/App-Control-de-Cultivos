package com.upchiapas.kt_template.cultivos.domain.model

data class Parcela(
    val id: Int,
    val nombre: String,
    val cultivo: String,
    val hectareas: Double,
    val fechaSiembra: String,
    val tieneRiego: Boolean
)