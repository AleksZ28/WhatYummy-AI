package com.azurowski.whatyummyai.main.ui.screens

import kotlinx.serialization.Serializable

@Serializable
data object HomeRoute

@Serializable
data class RecipeRoute(
    val recipeId: String,
    val recipeTitle: String,
    val recipeIsGlutenFree: Boolean
)

@Serializable
data class CookingRoute(
    val recipeId: String,
    val recipeTitle: String,
    val recipeIsGlutenFree: Boolean
)

@Serializable
data object AddRecipeRoute