package com.upchiapas.kt_template.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.upchiapas.kt_template.KtApplication
import com.upchiapas.kt_template.auth.presentation.screens.LoginScreen
import com.upchiapas.kt_template.auth.presentation.screens.RegisterScreen
import com.upchiapas.kt_template.auth.presentation.viewmodels.AuthViewModel
import com.upchiapas.kt_template.cultivos.presentation.screens.CultivosScreen
import com.upchiapas.kt_template.cultivos.presentation.viewmodels.CultivosViewModel

@Composable
fun AppNavigation(startDestination: String) {
    val navController = rememberNavController()
    val context = LocalContext.current
    val authViewModel: AuthViewModel = viewModel(factory = AuthViewModel.Factory)
    val cultivosViewModel: CultivosViewModel = viewModel(factory = CultivosViewModel.Factory)

    NavHost(navController = navController, startDestination = startDestination) {

        composable("login") {
            LoginScreen(
                viewModel = authViewModel,
                onLoginSuccess = {
                    navController.navigate("home") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onNavigateToRegister = {
                    navController.navigate("register")
                }
            )
        }

        composable("register") {
            RegisterScreen(
                viewModel = authViewModel,
                onRegisterSuccess = {
                    navController.navigate("home") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onNavigateToLogin = {
                    navController.popBackStack()
                }
            )
        }

        composable(route = "home") {
            CultivosScreen(
                viewModel = cultivosViewModel,
                onLogout = {
                    authViewModel.cerrarSesion()
                    navController.navigate("login") {
                        popUpTo(0)            }
                }
            )
        }
    }
}