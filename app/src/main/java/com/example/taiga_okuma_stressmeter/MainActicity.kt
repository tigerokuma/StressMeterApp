package com.example.taiga_okuma_stressmeter

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.taiga_okuma_stressmeter.ui.components.AppDrawer
import com.example.taiga_okuma_stressmeter.ui.screens.AppNavigation
import com.example.taiga_okuma_stressmeter.ui.theme.Taiga_Okuma_StressMeterTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Taiga_Okuma_StressMeterTheme {
                val drawerState = rememberDrawerState(DrawerValue.Closed) // Ensure drawer starts closed
                val scope = rememberCoroutineScope()
                val navController = rememberNavController()

                ModalNavigationDrawer(
                    drawerState = drawerState,
                    drawerContent = {
                        // Limit the width to 50% of the screen with white opacity background
                        AppDrawer(
                            modifier = Modifier
                                .width(180.dp) // Half the screen width (adjust as necessary)
                                .background(Color.White) // White with opacity
                        ) {
                            navController.navigate(it)
                            scope.launch { drawerState.close() } // Close drawer when item is clicked
                        }
                    }
                ) {
                    Scaffold(
                        topBar = {
                            TopAppBar(
                                title = { Text("Stress Meter") },
                                navigationIcon = {
                                    IconButton(onClick = {
                                        scope.launch {
                                            drawerState.open() // Open the drawer on user click
                                        }
                                    }) {
                                        Icon(
                                            imageVector = Icons.Default.Menu,
                                            contentDescription = "Menu"
                                        )
                                    }
                                }
                            )
                        },
                        content = { paddingValues ->
                            // Main content including navigation
                            AppNavigation(navController, onSubmit = { stressLevel ->
                                // Handle onSubmit action
                            })
                        }
                    )
                }
            }
        }
    }
}
