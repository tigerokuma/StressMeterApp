package com.example.taiga_okuma_stressmeter.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun AppDrawer(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Drawer header
        Text(
            text = "StressMeter",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(16.dp)
        )

        // Stress Meter option
        TextButton(
            onClick = { navController.navigate("stressMeter") }, // Navigate to StressMeterScreen
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Stress Meter", color = Color.Black)
        }

        // Results option
        TextButton(
            onClick = { navController.navigate("results") }, // Navigate to ResultScreen
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Results", color = Color.Black)
        }
    }
}
