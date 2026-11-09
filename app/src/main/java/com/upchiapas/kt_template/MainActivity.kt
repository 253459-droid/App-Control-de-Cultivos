package com.upchiapas.kt_template

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.upchiapas.kt_template.core.navigation.AppNavigation
import com.upchiapas.kt_template.ui.theme.Kt_templateTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val appContainer = (application as KtApplication).container
        val token = appContainer.sessionManager.fetchAuthToken()
        val startDestination = if (token != null) "home" else "login"

        setContent {
            Kt_templateTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation(startDestination = startDestination)
                }
            }
        }
    }
}