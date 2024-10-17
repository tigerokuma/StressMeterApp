package com.example.taiga_okuma_stressmeter.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun AppDrawer(
    modifier: Modifier = Modifier,  // Modifier to control the size, background, etc.
    onDestinationClicked: (String) -> Unit // Lambda to handle navigation
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Drawer header
        Text(
            text = "StressMeter",
            style = MaterialTheme.typography.headlineLarge,
            color = Color.Black,
            modifier = Modifier.padding(16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Stress Meter option
        TextButton(
            onClick = { onDestinationClicked("stressMeter") }, // Navigate to StressMeterScreen
            modifier = Modifier.fillMaxWidth().padding(8.dp)
        ) {
            Text("Stress Meter", color = Color.Black)
        }

        // Results option
        TextButton(
            onClick = { onDestinationClicked("results") }, // Navigate to ResultScreen
            modifier = Modifier.fillMaxWidth().padding(8.dp)
        ) {
            Text("Results", color = Color.Black)
        }
    }
}
