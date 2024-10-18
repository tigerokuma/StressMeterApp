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
    // List of all images you provided
    val allImages = listOf(
        R.drawable.fish_normal017,
        R.drawable.psm_alarm_clock,
        R.drawable.psm_alarm_clock2,
        R.drawable.psm_angry_face,
        R.drawable.psm_anxious,
        R.drawable.psm_baby_sleeping,
        R.drawable.psm_bar,
        R.drawable.psm_barbed_wire2,
        R.drawable.psm_beach3,
        R.drawable.psm_bird3,
        R.drawable.psm_blue_drop,
        R.drawable.psm_cat,
        R.drawable.psm_clutter,
        R.drawable.psm_clutter3,
        R.drawable.psm_dog_sleeping,
        R.drawable.psm_exam4,
        R.drawable.psm_gambling4,
        R.drawable.psm_headache,
        R.drawable.psm_headache2,
        R.drawable.psm_hiking3,
        R.drawable.psm_kettle,
        R.drawable.psm_lake3,
        R.drawable.psm_lawn_chairs3,
        R.drawable.psm_lonely,
        R.drawable.psm_lonely2,
        R.drawable.psm_mountains11,
        R.drawable.psm_neutral_child,
        R.drawable.psm_neutral_person2,
        R.drawable.psm_peaceful_person,
        R.drawable.psm_puppy,
        R.drawable.psm_puppy3,
        R.drawable.psm_reading_in_bed2,
        R.drawable.psm_running3,
        R.drawable.psm_running4,
        R.drawable.psm_sticky_notes2,
        R.drawable.psm_stressed_cat,
        R.drawable.psm_stressed_person,
        R.drawable.psm_stressed_person12,
        R.drawable.psm_stressed_person3,
        R.drawable.psm_stressed_person4,
        R.drawable.psm_stressed_person6,
        R.drawable.psm_stressed_person7,
        R.drawable.psm_stressed_person8,
        R.drawable.psm_talking_on_phone2,
        R.drawable.psm_to_do_list,
        R.drawable.psm_to_do_list3,
        R.drawable.psm_wine3,
        R.drawable.psm_work4,
        R.drawable.psm_yoga4
    )

    // Find the index of the image in the list
    val index = allImages.indexOf(imageResId)

    // Map the index to a stress level between 1 and 16
    return if (index != -1) {
        (index % 16) + 1  // This ensures the result is between 1 and 16
    } else {
        1  // Default to stress level 1 if the image is not found
    }
}

