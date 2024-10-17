package com.example.taiga_okuma_stressmeter.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ResultScreen(stressData: List<Pair<String, Int>>) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("A graph showing your Stress Levels")
        Spacer(modifier = Modifier.height(16.dp))

        // TODO: Implement a graph visualization using a Compose library like Charts
        Text("Graph would go here...")

        Spacer(modifier = Modifier.height(16.dp))

        Text("Summary of Results")

        Spacer(modifier = Modifier.height(16.dp))
        Text("Time\t\tStress Level")

        // Show the stress levels and times in a simple table
        stressData.forEach { (timestamp, stressLevel) ->
            Text("$timestamp\t\t$stressLevel")
        }
    }
}
