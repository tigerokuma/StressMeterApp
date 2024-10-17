package com.example.taiga_okuma_stressmeter.data

/**
 * A simple data class representing stress data.
 * @param timestamp The timestamp when the stress level was recorded (as a String or Long depending on your preference).
 * @param stressLevel The recorded stress level (an Int value).
 */
data class StressData(
    val timestamp: String,  // String representation of the timestamp, could be changed to Long if needed
    val stressLevel: Int    // The stress level (int)
)
