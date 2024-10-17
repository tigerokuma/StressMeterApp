package com.example.taiga_okuma_stressmeter.ui.screens

import ResultScreen
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.taiga_okuma_stressmeter.R
import com.example.taiga_okuma_stressmeter.ui.viewmodel.StressViewModel
import com.example.taiga_okuma_stressmeter.data.StressData  // Ensure this is the correct import

@Composable
fun AppNavigation(navController: NavHostController, onSubmit: (Int) -> Unit) {
    val stressViewModel: StressViewModel = viewModel()
    val stressData by remember { derivedStateOf { stressViewModel.getStressData() } }

    NavHost(navController, startDestination = "stressMeter") {
        // Stress Meter Screen
        composable("stressMeter") {
            StressMeterScreen(
                onImageClick = { imageResId ->
                    navController.navigate("stressDetail/$imageResId")
                },
                onSubmit = onSubmit,
                stressViewModel = stressViewModel,
                context = LocalContext.current
            )
        }

        // Stress Detail Screen
        composable("stressDetail/{imageResId}") { backStackEntry ->
            val context = LocalContext.current  // Get context outside the lambda
            val imageResId = backStackEntry.arguments?.getString("imageResId")?.toInt() ?: R.drawable.psm_stressed_person
            StressDetailScreen(
                imageResId = imageResId,
                onSubmit = {
                    // Handle submission here
                    val stressLevel = getStressLevelByImageId(imageResId)
                    stressViewModel.addStressData(
                        context = context,  // Use the context obtained earlier
                        timestamp = System.currentTimeMillis().toString(),
                        stressLevel = stressLevel
                    )
                    navController.popBackStack()  // Go back after submitting
                },
                onCancel = {
                    navController.popBackStack()  // Go back on cancel
                }
            )
        }



        // Results Screen Route
        composable("results") {
            val context = LocalContext.current
            LaunchedEffect(Unit) {
                stressViewModel.loadStressData(context)  // Load data from CSV on screen load
            }

            // Show results only when the data is loaded
            if (stressData.isNotEmpty()) {
                ResultScreen(stressData = stressData)
            } else {
                Text("Loading stress data...")
            }
        }
    }

}
fun getStressLevelByImageId(imageResId: Int): Int {
    return when (imageResId) {
        R.drawable.psm_stressed_person -> 1
        R.drawable.psm_alarm_clock -> 2
        R.drawable.psm_angry_face -> 3
        R.drawable.psm_anxious -> 4
        R.drawable.psm_baby_sleeping -> 5
        R.drawable.psm_bar -> 6
        R.drawable.psm_beach3 -> 7
        R.drawable.psm_dog_sleeping -> 8
        R.drawable.psm_gambling4 -> 9
        R.drawable.psm_headache -> 10
        R.drawable.psm_hiking3 -> 11
        R.drawable.psm_lake3 -> 12
        R.drawable.psm_lonely -> 13
        R.drawable.psm_peaceful_person -> 14
        R.drawable.psm_reading_in_bed2 -> 15
        R.drawable.psm_running3 -> 16
        else -> 1  // Default to stress level 1 if no match
    }
}
