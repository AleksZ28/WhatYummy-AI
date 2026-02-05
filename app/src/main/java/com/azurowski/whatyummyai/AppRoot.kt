package com.azurowski.whatyummyai

import android.content.Context.MODE_PRIVATE
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.azurowski.whatyummyai.main.ui.screens.AddRecipeRoute
import com.azurowski.whatyummyai.main.ui.screens.HomeRoute
import com.azurowski.whatyummyai.main.ui.screens.RecipeRoute
import com.azurowski.whatyummyai.main.ui.screens.home.HomeScreen
import com.azurowski.whatyummyai.main.ui.screens.recipe.AddRecipeScreen
import com.azurowski.whatyummyai.main.ui.screens.recipe.RecipeScreen
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
            startDestination = HomeRoute
        ) {
            composable<HomeRoute> {
                HomeScreen(navController)
            }

            composable<RecipeRoute> { backStackEntry ->
                val args = backStackEntry.toRoute<RecipeRoute>()

                RecipeScreen(
                    recipeId = args.recipeId,
                    recipeTitle = args.recipeTitle,
                    themeId = args.themeId,
                    navController = navController
                )
            }

            composable<AddRecipeRoute>(
                enterTransition = {
                    slideInVertically(
                        initialOffsetY = { it }
                    )
                },
                popExitTransition = {
                    slideOutVertically(
                        targetOffsetY = { it }
                    )
                }
            ) {
                AddRecipeScreen(navController)
            }
        }
    }
}