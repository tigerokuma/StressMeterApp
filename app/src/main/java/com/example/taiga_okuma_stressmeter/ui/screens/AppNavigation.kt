package com.example.taiga_okuma_stressmeter.ui.screens

import ResultScreen
import StressMeterScreen
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
fun AppNavigation(navController: NavHostController) {  // Removed onSubmit parameter from here
    val stressViewModel: StressViewModel = viewModel()
    val stressData by remember { derivedStateOf { stressViewModel.getStressData() } }

    NavHost(navController, startDestination = "stressMeter") {
        // Stress Meter Screen
        composable("stressMeter") {
            StressMeterScreen(
                onImageClick = { imageResId ->
                    navController.navigate("stressDetail/$imageResId")
                },
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
    // Mapping images to stress levels based on your provided information
    val stressLevelMap = mapOf(
        // Stress Level 1
        R.drawable.psm_beach3 to 1,
        R.drawable.psm_yoga4 to 1,
        R.drawable.psm_lawn_chairs3 to 1,

        // Stress Level 2
        R.drawable.psm_lake3 to 2,
        R.drawable.psm_mountains11 to 2,
        R.drawable.psm_hiking3 to 2,

        // Stress Level 3
        R.drawable.psm_peaceful_person to 3,
        R.drawable.psm_dog_sleeping to 3,
        R.drawable.psm_puppy3 to 3,

        // Stress Level 4
        R.drawable.psm_bird3 to 4,
        R.drawable.psm_cat to 4,
        R.drawable.psm_puppy to 4,

        // Stress Level 5
        R.drawable.psm_blue_drop to 5,
        R.drawable.psm_barbed_wire2 to 5,
        R.drawable.psm_bar to 5,

        // Stress Level 6
        R.drawable.psm_anxious to 6,
        R.drawable.psm_neutral_person2 to 6,
        R.drawable.psm_neutral_child to 6,

        // Stress Level 7
        R.drawable.psm_clutter3 to 7,
        R.drawable.psm_sticky_notes2 to 7,
        R.drawable.psm_alarm_clock to 7,

        // Stress Level 8
        R.drawable.psm_stressed_person7 to 8,
        R.drawable.psm_lonely to 8,
        R.drawable.psm_lonely2 to 8,

        // Stress Level 9
        R.drawable.psm_to_do_list3 to 9,
        R.drawable.psm_to_do_list to 9,
        R.drawable.psm_running3 to 9,
        R.drawable.psm_angry_face to 9,

        // Stress Level 10
        R.drawable.psm_stressed_person6 to 10,
        R.drawable.psm_stressed_person7 to 10,

        // Stress Level 11
        R.drawable.psm_work4 to 11,
        R.drawable.psm_talking_on_phone2 to 11,
        R.drawable.psm_kettle to 11,

        // Stress Level 12
        R.drawable.psm_exam4 to 12,
        R.drawable.psm_stressed_person4 to 12,
        R.drawable.psm_running4 to 12,

        // Stress Level 13
        R.drawable.psm_reading_in_bed2 to 13,
        R.drawable.psm_alarm_clock2 to 13,
        R.drawable.psm_stressed_person12 to 13,

        // Stress Level 14 (added two more images)
        R.drawable.psm_stressed_person3 to 14,
        R.drawable.psm_gambling4 to 14,
        R.drawable.psm_headache to 14,

        // Stress Level 15 (added two more images)
        R.drawable.psm_stressed_person4 to 15,
        R.drawable.psm_headache2 to 15,
        R.drawable.psm_clutter to 15,

        // Stress Level 16 (added two more images)
        R.drawable.psm_lonely2 to 16,
        R.drawable.psm_wine3 to 16,
        R.drawable.psm_stressed_cat to 16
    )

    // Return the mapped stress level, or 1 if the imageResId is not found
    return stressLevelMap[imageResId] ?: 1
}
