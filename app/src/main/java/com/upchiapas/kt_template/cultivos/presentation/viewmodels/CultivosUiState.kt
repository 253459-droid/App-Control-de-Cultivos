package com.upchiapas.kt_template.cultivos.presentation.viewmodels

import com.upchiapas.kt_template.cultivos.domain.model.Parcela

sealed interface CultivosUiState {
    object Cargando : CultivosUiState
    data class Exito(val parcelas: List<Parcela>) : CultivosUiState
    data class Error(val mensaje: String) : CultivosUiState
}