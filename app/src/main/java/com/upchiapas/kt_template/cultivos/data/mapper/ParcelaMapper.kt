package com.upchiapas.kt_template.cultivos.data.mapper

import com.upchiapas.kt_template.cultivos.data.dto.ParcelaDto
import com.upchiapas.kt_template.cultivos.domain.model.Parcela

fun ParcelaDto.toDomain(): Parcela {
    return Parcela(
        id = id ?: 0,
        nombre = nombre,
        cultivo = cultivo,
        hectareas = hectareas,
        fechaSiembra = fechaSiembra,
        tieneRiego = tieneRiego
    )
}

fun Parcela.toDto(): ParcelaDto {
    return ParcelaDto(
        id = if (id == 0) null else id,
        nombre = nombre,
        cultivo = cultivo,
        hectareas = hectareas,
        fechaSiembra = fechaSiembra,
        tieneRiego = tieneRiego
    )
}