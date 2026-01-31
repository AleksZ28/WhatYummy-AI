package com.azurowski.whatyummyai

import android.content.Context.MODE_PRIVATE
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.azurowski.whatyummyai.main.ui.screens.HomeScreen
import com.azurowski.whatyummyai.main.ui.theme.Backgrounds

@Composable
fun AppNav() {
    val navController = rememberNavController()

    val context = LocalContext.current
    val prefs = context.getSharedPreferences("MyPrefs", MODE_PRIVATE)
    val savedThemeKey = prefs.getInt("themeId", 1)

    val savedTheme = when(savedThemeKey) {
        1 -> Backgrounds.bg1
        2 -> Backgrounds.bg2
        3 -> Backgrounds.bg3
        else -> Backgrounds.bg1
    }

    Box(modifier = Modifier.fillMaxSize().background(brush = savedTheme)){
        NavHost(
            navController = navController,
            startDestination = "home"
        ) {
            composable("home") {
                HomeScreen(navController)
            }
        }
    }
}