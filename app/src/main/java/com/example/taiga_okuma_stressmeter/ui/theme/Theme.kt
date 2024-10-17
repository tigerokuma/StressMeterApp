package com.example.taiga_okuma_stressmeter.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

// Define your custom dark color scheme
private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

// Define your custom light color scheme
private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40

    // Optionally, you can override other colors like background and surface
    /*
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

// Main Theme Composable function
@Composable
fun Taiga_Okuma_StressMeterTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),  // Check system theme
    dynamicColor: Boolean = true,                // Enable dynamic color for Android 12+
    content: @Composable () -> Unit
) {
    // Choose color scheme based on dynamic color, system dark theme, and device API level
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            // Use dynamic color scheme if available (Android 12+)
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme  // Use custom dark color scheme
        else -> LightColorScheme      // Use custom light color scheme
    }

    // Apply Material 3 theme with color scheme and typography
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,  // Use Typography defined in Type.kt
        content = content         // Provide the composable content to render within the theme
    )
}
