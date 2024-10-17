package com.example.taiga_okuma_stressmeter.util

import android.content.Context
import com.example.taiga_okuma_stressmeter.data.StressData
import java.io.File
import java.io.FileWriter


object CSVUtil {
    // Function to save stress data to CSV
    fun saveStressData(context: Context, stressData: StressData) {
        val file = File(context.filesDir, "stress_data.csv")

        // Append data to the CSV file (create file if it doesn't exist)
        FileWriter(file, true).use { writer ->
            writer.append("${stressData.timestamp},${stressData.stressLevel}\n")
        }
    }

    // Function to load stress data from CSV
    fun loadStressData(context: Context): List<StressData> {
        val file = File(context.filesDir, "stress_data.csv")
        if (!file.exists()) return emptyList()

        return file.readLines().map { line ->
            val (timestamp, stressLevel) = line.split(",")
            StressData(timestamp, stressLevel.toInt())
        }
    }
}
