package com.example.taiga_okuma_stressmeter.ui.screens

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.taiga_okuma_stressmeter.R
import com.example.taiga_okuma_stressmeter.ui.viewmodel.StressViewModel

@Composable
fun AppNavigation(navController: NavHostController, onSubmit: (Int) -> Unit) {
    val stressViewModel: StressViewModel = viewModel()

    NavHost(navController, startDestination = "stressMeter") {
        composable("stressMeter") {
            StressMeterScreen(
                onImageClick = { imageResId ->  // Pass the clicked image resource ID
                    navController.navigate("stressDetail/$imageResId")
                },
                onSubmit = onSubmit,
                stressViewModel = stressViewModel
            )
        }

        // Detail Screen Route: stressDetail/{imageResId}
        composable("stressDetail/{imageResId}") { backStackEntry ->
            val imageResId = backStackEntry.arguments?.getString("imageResId")?.toInt() ?: R.drawable.psm_stressed_person
            StressDetailScreen(
                imageResId = imageResId,  // Use imageResId directly
                onSubmit = {
                    navController.popBackStack()  // Return to the StressMeter screen after submitting
                },
                onCancel = {
                    navController.popBackStack()  // Simply go back to the StressMeter screen
                }
            )
        }
    }
}



