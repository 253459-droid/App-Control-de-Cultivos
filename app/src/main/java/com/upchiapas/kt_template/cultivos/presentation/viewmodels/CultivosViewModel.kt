package com.upchiapas.kt_template.cultivos.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.upchiapas.kt_template.KtApplication
import com.upchiapas.kt_template.cultivos.domain.model.Parcela
import com.upchiapas.kt_template.cultivos.domain.repository.CultivosRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CultivosViewModel(private val repository: CultivosRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<CultivosUiState>(CultivosUiState.Cargando)
    val uiState: StateFlow<CultivosUiState> = _uiState.asStateFlow()

    init {
        getParcelas()
    }

    fun getParcelas() {
        _uiState.value = CultivosUiState.Cargando
        viewModelScope.launch {
            val result = repository.getParcelas()
            if (result.isSuccess) {
                _uiState.value = CultivosUiState.Exito(result.getOrDefault(emptyList()))
            } else {
                _uiState.value = CultivosUiState.Error(result.exceptionOrNull()?.message ?: "Error desconocido")
            }
        }
    }

    fun createParcela(nombre: String, cultivo: String, hectareas: Double, fechaSiembra: String, tieneRiego: Boolean) {
        viewModelScope.launch {
            val nuevaParcela = Parcela(0, nombre, cultivo, hectareas, fechaSiembra, tieneRiego)
            val result = repository.createParcela(nuevaParcela)
            if (result.isSuccess) {
                getParcelas()
            }
        }
    }

    fun updateParcela(id: Int, nombre: String, cultivo: String, hectareas: Double, fechaSiembra: String, tieneRiego: Boolean) {
        viewModelScope.launch {
            val parcelaActualizada = Parcela(id, nombre, cultivo, hectareas, fechaSiembra, tieneRiego)
            val result = repository.updateParcela(id, parcelaActualizada)
            if (result.isSuccess) {
                getParcelas()
            }
        }
    }

    fun deleteParcela(id: Int) {
        viewModelScope.launch {
            val result = repository.deleteParcela(id)
            if (result.isSuccess) {
                getParcelas()
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as KtApplication)
                val repository = application.container.cultivosRepository
                CultivosViewModel(repository)
            }
        }
    }
}