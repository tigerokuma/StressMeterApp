package com.example.taiga_okuma_stressmeter.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.taiga_okuma_stressmeter.ui.viewmodel.StressViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StressMeterScreen(
    onSubmit: (Int) -> Unit,
    stressViewModel: StressViewModel = viewModel() // Inject the ViewModel
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Stress Meter") }
            )
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding) // Use the padding provided by Scaffold
                    .padding(16.dp)
            ) {
                Text(
                    "Touch the image that best captures how stressed you feel right now",
                    modifier = Modifier.padding(16.dp)
                )

                // Display a 4x4 grid of images from local drawable resources
                LazyVerticalGrid(
                    columns = GridCells.Fixed(4), // 4 columns
                    modifier = Modifier.fillMaxSize().padding(4.dp),
                    contentPadding = PaddingValues(4.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp), // Spacing between rows
                    horizontalArrangement = Arrangement.spacedBy(4.dp) // Spacing between columns
                ) {
                    itemsIndexed(stressViewModel.currentImages) { index, imageResId ->
                        // Map stress levels from 1 to 16, starting from the current page index
                        val stressLevel = stressViewModel.currentPage * stressViewModel.imagesPerPage + index + 1

                        Image(
                            painter = painterResource(id = imageResId),  // Load images from drawable
                            contentDescription = "Stress Image $stressLevel",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .aspectRatio(1f)  // Ensures images have a 1:1 aspect ratio
                                .clickable { stressViewModel.selectStressLevel(stressLevel) } // Update stress level on click
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Show the selected stress level and a submit button
                Text("Selected Stress Level: ${if (stressViewModel.selectedStressLevel != -1) stressViewModel.selectedStressLevel else "None"}")

                Row(modifier = Modifier.padding(16.dp)) {
                    // Submit button
                    Button(
                        onClick = {
                            if (stressViewModel.selectedStressLevel != -1) {
                                onSubmit(stressViewModel.selectedStressLevel) // Pass the selected level
                            }
                        },
                        modifier = Modifier.padding(end = 16.dp),
                        enabled = stressViewModel.selectedStressLevel != -1 // Disable button if no selection
                    ) {
                        Text("Submit")
                    }

                    // More Images button (load the next page of images)
                    Button(
                        onClick = { stressViewModel.loadNextPage() }, // Increment the page to show the next set of images
                        enabled = stressViewModel.hasMoreImages // Disable if no more images
                    ) {
                        Text("More Images")
                    }
                }
            }
        }
    )
}
