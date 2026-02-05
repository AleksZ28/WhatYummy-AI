package com.azurowski.whatyummyai.main.ui.screens

import kotlinx.serialization.Serializable

@Serializable
data object HomeRoute

@Serializable
data class RecipeRoute(
    val recipeId: String,
    val recipeTitle: String,
    val themeId: Int
)

@Serializable
data object AddRecipeRoute