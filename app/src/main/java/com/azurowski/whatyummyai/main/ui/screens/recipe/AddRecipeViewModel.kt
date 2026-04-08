package com.azurowski.whatyummyai.main.ui.screens.recipe

import androidx.compose.foundation.text.input.TextFieldState
import androidx.lifecycle.ViewModel
import com.azurowski.whatyummyai.main.model.IngredientField
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AddRecipeViewModel : ViewModel() {
    private val _ingredientsData = MutableStateFlow<List<IngredientField>>(listOf(IngredientField()))
    val ingredientsData: StateFlow<List<IngredientField>> = _ingredientsData

    private val _instructions = MutableStateFlow<List<TextFieldState>>(listOf(TextFieldState()))
    val instructions: StateFlow<List<TextFieldState>> = _instructions

    fun addIngredient() {
        _ingredientsData.value += IngredientField()
    }

    fun addInstruction() {
        _instructions.value += TextFieldState()
    }
}