package com.example.taiga_okuma_stressmeter

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
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
                // SnackbarHostState to manage snackbars
                val snackbarHostState = remember { SnackbarHostState() }
                val scope = rememberCoroutineScope()

                // Navigation controller
                val navController = rememberNavController()

                // Drawer state for opening/closing the drawer
                val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

                // ModalNavigationDrawer for handling the drawer
                ModalNavigationDrawer(
                    drawerState = drawerState,
                    drawerContent = {
                        AppDrawer(navController)  // Drawer content
                    }
                ) {
                    // Scaffold for Material 3 with SnackbarHost
                    Scaffold(
                        topBar = {
                            TopAppBar(
                                title = { Text("Stress Meter") }
                            )
                        },
                        snackbarHost = {
                            SnackbarHost(snackbarHostState)  // Host for snackbars
                        },
                    ) {
                        // Main content and navigation
                        AppNavigation(navController)

                        // Example button to trigger a snackbar
                        Button(
                            onClick = {
                                scope.launch {
                                    snackbarHostState.showSnackbar(
                                        message = "This is a snackbar!",
                                        actionLabel = "Dismiss",
                                        duration = SnackbarDuration.Short
                                    )
                                }
                            },
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text("Show Snackbar")
                        }
                    }
                }
            }
        }
    }
}
