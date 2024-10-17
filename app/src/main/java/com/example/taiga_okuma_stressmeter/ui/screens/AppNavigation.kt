package com.example.taiga_okuma_stressmeter.ui.screens

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.taiga_okuma_stressmeter.ui.viewmodel.StressViewModel

@Composable
fun AppNavigation(navController: NavHostController, onSubmit: (Int) -> Unit) {
    val stressViewModel: StressViewModel = viewModel()  // Retrieve the ViewModel instance

    NavHost(navController, startDestination = "stressMeter") {
        composable("stressMeter") {
            StressMeterScreen(
                onImageClick = { selectedStressLevel: Int ->  // Pass the clicked image's stress level
                    navController.navigate("stressDetail/$selectedStressLevel")
                },
                onSubmit = onSubmit,  // Pass the onSubmit parameter
                stressViewModel = stressViewModel
            )
        }

        // Detail Screen Route: stressDetail/{selectedStressLevel}
        composable("stressDetail/{selectedStressLevel}") { backStackEntry ->
            val stressLevel = backStackEntry.arguments?.getString("selectedStressLevel")?.toInt() ?: -1
            StressDetailScreen(
                stressLevel = stressLevel,
                onSubmit = {
                    onSubmit(stressLevel)  // Pass the stress level when submitting
                    navController.popBackStack()  // Return to the StressMeter screen after submitting
                },
                onCancel = {
                    navController.popBackStack()  // Simply go back to the StressMeter screen
                }
            )
        }
    }
}


