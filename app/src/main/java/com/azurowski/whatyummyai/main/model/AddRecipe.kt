package com.azurowski.whatyummyai.main.model

import android.net.Uri
import androidx.compose.foundation.text.input.TextFieldState

data class AddRecipeState(
    val title: TextFieldState = TextFieldState(),
    val totalTime: TextFieldState = TextFieldState(),
    val timeUnit: String = "min",
    val images: List<Uri> = emptyList(),
    val ingredients: List<IngredientField> = listOf(IngredientField()),
    val instructions: List<TextFieldState> = listOf(TextFieldState()),
    val categories: List<String> = emptyList(),
    val glutenFree: Boolean = false,
    val public: Boolean = true,
    val isSaving: Boolean = false
)

data class IngredientField(
    val name: TextFieldState = TextFieldState(),
    val amount: TextFieldState = TextFieldState(),
    val unit: String = ""
)