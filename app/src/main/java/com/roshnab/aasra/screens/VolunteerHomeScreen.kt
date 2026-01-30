package com.roshnab.aasra.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.roshnab.aasra.components.AasraBottomBar
import com.roshnab.aasra.components.AasraTopBar
import com.roshnab.aasra.components.BottomNavScreen
import com.roshnab.aasra.data.ProfileViewModel

@Composable
fun VolunteerHomeScreen(onLogoutClick: () -> Unit) {
    var currentScreen by remember { mutableStateOf(BottomNavScreen.Home) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background) // <--- FIX IS HERE
    ) {

        Box(modifier = Modifier.fillMaxSize()) {
            when (currentScreen) {
                BottomNavScreen.Home -> {
                    Scaffold(
                        topBar = {
                            AasraTopBar(
                                onProfileClick = { currentScreen = BottomNavScreen.Profile },
                                onNotificationClick = {}
                            )
                        }
                    ) { padding ->
                        Box(modifier = Modifier.padding(padding).fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text("Volunteer Map View \n(Red Markers will appear here)")
                        }
                    }
                }
                BottomNavScreen.Requests -> {
                    VolunteerRequestListScreen()
                }
                BottomNavScreen.Safety -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("Safety Guidelines & Protocols")
                    }
                }
                BottomNavScreen.Profile -> {
                    Box(modifier = Modifier.padding(bottom = 100.dp)) {
                        val profileViewModel: ProfileViewModel = viewModel()
                        ProfileScreen(
                            onBackClick = { currentScreen = BottomNavScreen.Home },
                            onLogoutClick = onLogoutClick,
                            onAddLocationClick = {},
                            onEditProfileClick = {},
                            isDarkTheme = false,
                            onThemeChanged = {},
                            onSupportClick = {},
                            viewModel = profileViewModel
                        )
                    }
                }
                else -> {}
            }
        }

        Column(
            modifier = Modifier.align(Alignment.BottomCenter).fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AasraBottomBar(
                currentScreen = currentScreen,
                items = listOf(
                    BottomNavScreen.Home,
                    BottomNavScreen.Requests,
                    BottomNavScreen.Safety,
                    BottomNavScreen.Profile
                ),
                onScreenSelected = { screen -> currentScreen = screen }
            )
        }
    }
}