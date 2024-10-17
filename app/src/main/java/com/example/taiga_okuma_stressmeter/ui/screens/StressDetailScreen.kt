package com.example.taiga_okuma_stressmeter.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.taiga_okuma_stressmeter.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StressDetailScreen(
    stressLevel: Int,
    onSubmit: () -> Unit,
    onCancel: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Stress Detail") }
            )
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp)
            ) {
                // Show the selected image
                Text("Selected Stress Level: $stressLevel")

                // Display the selected image in the center
                Image(
                    painter = painterResource(id = getImageByStressLevel(stressLevel)),
                    contentDescription = "Selected Stress Level Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .padding(16.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row {
                    Button(
                        onClick = onSubmit,  // Submit the selected stress level
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text("Submit")
                    }

                    Button(
                        onClick = onCancel,  // Cancel and go back
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text("Cancel")
                    }
                }
            }
        }
    )
}

// Function to return the drawable resource based on stress level
fun getImageByStressLevel(stressLevel: Int): Int {
    return when (stressLevel) {
        1 -> R.drawable.psm_stressed_person
        2 -> R.drawable.psm_alarm_clock
        3 -> R.drawable.psm_angry_face
        4 -> R.drawable.psm_anxious
        5 -> R.drawable.psm_baby_sleeping
        6 -> R.drawable.psm_bar
        7 -> R.drawable.psm_beach3
        8 -> R.drawable.psm_dog_sleeping
        9 -> R.drawable.psm_gambling4
        10 -> R.drawable.psm_headache
        11 -> R.drawable.psm_hiking3
        12 -> R.drawable.psm_lake3
        13 -> R.drawable.psm_lonely
        14 -> R.drawable.psm_peaceful_person
        15 -> R.drawable.psm_reading_in_bed2
        16 -> R.drawable.psm_running3
        else -> R.drawable.psm_stressed_person  // Default image if no match
    }
}
