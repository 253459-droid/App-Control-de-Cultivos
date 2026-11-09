package com.upchiapas.kt_template.auth.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.upchiapas.kt_template.auth.presentation.viewmodels.AuthUiState
import com.upchiapas.kt_template.auth.presentation.viewmodels.AuthViewModel

@Composable
fun LoginScreen(
    viewModel: AuthViewModel,
    onLoginSuccess: () -> Unit,
    onNavigateToRegister: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var email by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }

    LaunchedEffect(uiState) {
        if (uiState is AuthUiState.Exito) {
            onLoginSuccess()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Iniciar Sesión", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Correo electrónico") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = contrasena,
            onValueChange = { contrasena = it },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(24.dp))

        if (uiState is AuthUiState.Error) {
            Text(
                text = (uiState as AuthUiState.Error).mensaje,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        if (uiState is AuthUiState.Cargando) {
            CircularProgressIndicator()
        } else {
            Button(
                onClick = { viewModel.login(email, contrasena) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Entrar")
            }
            Spacer(modifier = Modifier.height(8.dp))
            TextButton(onClick = onNavigateToRegister) {
                Text("¿No tienes cuenta? Regístrate")
            }
        }
    }
}