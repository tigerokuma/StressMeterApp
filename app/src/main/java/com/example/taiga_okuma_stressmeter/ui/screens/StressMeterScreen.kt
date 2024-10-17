package com.example.taiga_okuma_stressmeter.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter

@Composable
fun StressMeterScreen(onSubmit: (Int) -> Unit) {
    val selectedStressLevel = remember { mutableStateOf(-1) }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        Text(
            "Touch the image that best captures how stressed you feel right now",
            modifier = Modifier.padding(16.dp)
        )

        // Display a grid of images
        LazyColumn {
            itemsIndexed(getImageUrls()) { index, imageUrl ->
                Image(
                    painter = rememberImagePainter(data = imageUrl),
                    contentDescription = "Stress Image $index",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .padding(8.dp)
                        .clickable { selectedStressLevel.value = index + 1 } // Update stress level on click
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Show the selected stress level and a submit button
        Text("Selected Stress Level: ${if (selectedStressLevel.value != -1) selectedStressLevel.value else "None"}")

        Button(
            onClick = {
                if (selectedStressLevel.value != -1) {
                    onSubmit(selectedStressLevel.value) // Pass the selected level
                }
            },
            modifier = Modifier.padding(16.dp),
            enabled = selectedStressLevel.value != -1 // Disable button if no selection
        ) {
            Text("Submit")
        }
    }
}

// Mock function to provide a list of image URLs (replace with actual image references)
fun getImageUrls(): List<String> {
    return listOf(
        "https://link_to_image_1.jpg",
        "https://link_to_image_2.jpg",
        "https://link_to_image_3.jpg",
        "https://link_to_image_4.jpg"
    )
}
