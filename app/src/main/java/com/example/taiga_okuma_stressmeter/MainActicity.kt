package com.example.taiga_okuma_stressmeter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.taiga_okuma_stressmeter.ui.theme.StressMeterTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StressMeterTheme {
                val navController = rememberNavController()
                Scaffold(
                    topBar = { TopAppBar(title = { Text("Stress Meter") }) },
                    drawerContent = { AppDrawer(navController) }
                ) {
                    AppNavigation(navController)
                }
            }
        }
    }
}

@Composable
fun AppDrawer(navController: NavHostController) {
    Column {
        TextButton(onClick = { navController.navigate("stressMeter") }) {
            Text("Stress Meter")
        }
        TextButton(onClick = { navController.navigate("results") }) {
            Text("Results")
        }
    }
}

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController, startDestination = "stressMeter") {
        composable("stressMeter") { StressMeterScreen() }
        composable("results") { ResultScreen() }
    }
}
