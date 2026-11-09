package com.upchiapas.kt_template.cultivos.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.upchiapas.kt_template.cultivos.domain.model.Parcela
import com.upchiapas.kt_template.cultivos.presentation.viewmodels.CultivosUiState
import com.upchiapas.kt_template.cultivos.presentation.viewmodels.CultivosViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CultivosScreen(
    viewModel: CultivosViewModel,
    onLogout: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var showDialog by remember { mutableStateOf(false) }
    var parcelaAEditar by remember { mutableStateOf<Parcela?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mis Cultivos") },
                actions = {
                    TextButton(onClick = onLogout) {
                        Text("Salir", color = MaterialTheme.colorScheme.error)
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                parcelaAEditar = null
                showDialog = true
            }) {
                Icon(Icons.Default.Add, contentDescription = "Agregar Parcela")
            }
        }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize().padding(padding)) {
            when (uiState) {
                is CultivosUiState.Cargando -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                is CultivosUiState.Error -> {
                    Text(
                        text = (uiState as CultivosUiState.Error).mensaje,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                is CultivosUiState.Exito -> {
                    val parcelas = (uiState as CultivosUiState.Exito).parcelas
                    if (parcelas.isEmpty()) {
                        Text("No tienes parcelas registradas.", modifier = Modifier.align(Alignment.Center))
                    } else {
                        LazyColumn(modifier = Modifier.fillMaxSize(), contentPadding = PaddingValues(16.dp)) {
                            items(parcelas) { parcela ->
                                ParcelaItem(
                                    parcela = parcela,
                                    onEdit = {
                                        parcelaAEditar = parcela
                                        showDialog = true
                                    },
                                    onDelete = { viewModel.deleteParcela(parcela.id) }
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                            }
                        }
                    }
                }
            }
        }
    }

    if (showDialog) {
        ParcelaDialog(
            parcela = parcelaAEditar,
            onDismiss = { showDialog = false },
            onSave = { nombre, cultivo, hectareas, fecha, riego ->
                if (parcelaAEditar == null) {
                    viewModel.createParcela(nombre, cultivo, hectareas, fecha, riego)
                } else {
                    viewModel.updateParcela(parcelaAEditar!!.id, nombre, cultivo, hectareas, fecha, riego)
                }
                showDialog = false
            }
        )
    }
}

@Composable
fun ParcelaItem(parcela: Parcela, onEdit: () -> Unit, onDelete: () -> Unit) {
    Card(modifier = Modifier.fillMaxWidth(), elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(text = parcela.nombre, style = MaterialTheme.typography.titleMedium)
                Text(text = "Cultivo: ${parcela.cultivo}", style = MaterialTheme.typography.bodyMedium)
                Text(text = "${parcela.hectareas} ha - Riego: ${if(parcela.tieneRiego) "Sí" else "No"}", style = MaterialTheme.typography.bodySmall)
            }
            Row {
                IconButton(onClick = onEdit) { Icon(Icons.Default.Edit, contentDescription = "Editar", tint = MaterialTheme.colorScheme.primary) }
                IconButton(onClick = onDelete) { Icon(Icons.Default.Delete, contentDescription = "Eliminar", tint = MaterialTheme.colorScheme.error) }
            }
        }
    }
}

@Composable
fun ParcelaDialog(
    parcela: Parcela?,
    onDismiss: () -> Unit,
    onSave: (String, String, Double, String, Boolean) -> Unit
) {
    var nombre by remember { mutableStateOf(parcela?.nombre ?: "") }
    var cultivo by remember { mutableStateOf(parcela?.cultivo ?: "") }
    var hectareas by remember { mutableStateOf(parcela?.hectareas?.toString() ?: "") }
    var fecha by remember { mutableStateOf(parcela?.fechaSiembra ?: "") }
    var riego by remember { mutableStateOf(parcela?.tieneRiego ?: false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(if (parcela == null) "Nueva Parcela" else "Editar Parcela") },
        text = {
            Column {
                OutlinedTextField(value = nombre, onValueChange = { nombre = it }, label = { Text("Nombre de parcela") })
                OutlinedTextField(value = cultivo, onValueChange = { cultivo = it }, label = { Text("Cultivo") })
                OutlinedTextField(value = hectareas, onValueChange = { hectareas = it }, label = { Text("Hectáreas") })
                OutlinedTextField(value = fecha, onValueChange = { fecha = it }, label = { Text("Fecha de siembra") })
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(top = 8.dp)) {
                    Checkbox(checked = riego, onCheckedChange = { riego = it })
                    Text("Tiene sistema de riego")
                }
            }
        },
        confirmButton = {
            Button(onClick = {
                val has = hectareas.toDoubleOrNull() ?: 0.0
                onSave(nombre, cultivo, has, fecha, riego)
            }) { Text("Guardar") }
        },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Cancelar") } }
    )
}