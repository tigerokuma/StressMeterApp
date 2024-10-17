package com.example.taiga_okuma_stressmeter.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.taiga_okuma_stressmeter.R

@Composable
fun StressMeterScreen(onSubmit: (Int) -> Unit) {
    val selectedStressLevel = remember { mutableStateOf(-1) }
    var currentPage by remember { mutableStateOf(0) }  // Keep track of the current page (to load more images)

    // Get the list of images in batches of 16
    val imagesPerPage = 16
    val currentImages = getLocalImages().drop(currentPage * imagesPerPage).take(imagesPerPage)

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        Text(
            "Touch the image that best captures how stressed you feel right now",
            modifier = Modifier.padding(16.dp)
        )

        // Display a 4x4 grid of images from local drawable resources
        LazyVerticalGrid(
            columns = GridCells.Fixed(4), // 4 columns
            modifier = Modifier.fillMaxSize().padding(8.dp),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp), // Spacing between rows
            horizontalArrangement = Arrangement.spacedBy(8.dp) // Spacing between columns
        ) {
            itemsIndexed(currentImages) { index, imageResId ->
                // Map stress levels from 1 to 16, starting from the current page index
                val stressLevel = currentPage * imagesPerPage + index + 1

                Image(
                    painter = painterResource(id = imageResId),  // Load images from drawable
                    contentDescription = "Stress Image $stressLevel",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .aspectRatio(1f)  // Ensures images have a 1:1 aspect ratio
                        .clickable { selectedStressLevel.value = stressLevel } // Update stress level on click
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Show the selected stress level and a submit button
        Text("Selected Stress Level: ${if (selectedStressLevel.value != -1) selectedStressLevel.value else "None"}")

        Row(modifier = Modifier.padding(16.dp)) {
            // Submit button
            Button(
                onClick = {
                    if (selectedStressLevel.value != -1) {
                        onSubmit(selectedStressLevel.value) // Pass the selected level
                    }
                },
                modifier = Modifier.padding(end = 16.dp),
                enabled = selectedStressLevel.value != -1 // Disable button if no selection
            ) {
                Text("Submit")
            }

            // More Images button (load the next page of images)
            Button(
                onClick = { currentPage++ }, // Increment the page to show the next set of images
                enabled = (currentPage + 1) * imagesPerPage < getLocalImages().size // Disable if no more images
            ) {
                Text("More Images")
            }
        }
    }
}
// Function to provide a list of drawable resource IDs
fun getLocalImages(): List<Int> {
    return listOf(
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
}
