package com.example.taiga_okuma_stressmeter.ui.screens

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.taiga_okuma_stressmeter.ui.viewmodel.StressViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StressMeterScreen(
    onSubmit: (Int) -> Unit,
    stressViewModel: StressViewModel = viewModel(),
    onImageClick: (Int) -> Unit,
    context: Context  // Add context as a parameter
) {
    val coroutineScope = rememberCoroutineScope()

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
                    .padding(innerPadding)
                    .padding(16.dp)
            ) {
                Text(
                    "Touch the image that best captures how stressed you feel right now",
                    modifier = Modifier.padding(16.dp)
                )

                LazyVerticalGrid(
                    columns = GridCells.Fixed(4),
                    modifier = Modifier.fillMaxSize().padding(4.dp),
                    contentPadding = PaddingValues(4.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    itemsIndexed(stressViewModel.currentImages) { index, imageResId ->
                        Image(
                            painter = painterResource(id = imageResId),
                            contentDescription = "Stress Image $imageResId",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .aspectRatio(1f)
                                .clickable {
                                    onImageClick(imageResId)
                                    stressViewModel.selectStressLevel(index + 1)
                                }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text("Selected Stress Level: ${if (stressViewModel.selectedStressLevel != -1) stressViewModel.selectedStressLevel else "None"}")

                Row(modifier = Modifier.padding(16.dp)) {
                    Button(
                        onClick = {
                            if (stressViewModel.selectedStressLevel != -1) {
                                val timestamp = System.currentTimeMillis().toString()

                                coroutineScope.launch {
                                    stressViewModel.addStressData(
                                        context,  // Pass context to save the CSV
                                        timestamp,
                                        stressViewModel.selectedStressLevel
                                    )
                                }

                                onSubmit(stressViewModel.selectedStressLevel)
                            }
                        },
                        modifier = Modifier.padding(end = 16.dp),
                        enabled = stressViewModel.selectedStressLevel != -1
                    ) {
                        Text("Submit")
                    }

                    Button(
                        onClick = { stressViewModel.loadNextPage() },
                        enabled = stressViewModel.hasMoreImages
                    ) {
                        Text("More Images")
                    }
                }
            }
        }
    )
}
