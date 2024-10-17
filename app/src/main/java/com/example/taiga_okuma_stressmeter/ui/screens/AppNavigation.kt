package com.example.taiga_okuma_stressmeter.ui.screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController, startDestination = "stressMeter") {

        // Navigate to StressMeterScreen with onSubmit handling
        composable("stressMeter") {
            StressMeterScreen(
                onSubmit = { stressLevel ->
                    // Handle the stress level submission
                    // For example, navigate to results or show a message
                    println("Stress level submitted: $stressLevel")
                }
            )
        }

        // Navigate to ResultScreen (Pass the data to visualize if needed)
        composable("results") {
            ResultScreen(emptyList())  // You can pass actual data here
        }
    }
}
