package com.example.taiga_okuma_stressmeter.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
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
    imageResId: Int,  // Receive the image resource ID
    onSubmit: () -> Unit,  // Change to regular lambda
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
                // Display the selected image
                Image(
                    painter = painterResource(id = imageResId),
                    contentDescription = "Selected Stress Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .padding(16.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Center the buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    // Cancel Button
                    OutlinedButton(
                        onClick = onCancel,
                        modifier = Modifier.padding(end = 16.dp)
                    ) {
                        Text("Cancel")
                    }

                    // Submit Button
                    Button(
                        onClick = onSubmit  // Use the submit action
                    ) {
                        Text("Submit")
                    }
                }
            }
        }
    )
}
