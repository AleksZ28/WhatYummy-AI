package com.azurowski.whatyummyai.main.ui.screens.recipe

import androidx.compose.foundation.text.input.TextFieldState
import androidx.lifecycle.ViewModel
import com.azurowski.whatyummyai.main.model.AddRecipeState
import com.azurowski.whatyummyai.main.model.IngredientField
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AddRecipeViewModel : ViewModel() {
    private val _state = MutableStateFlow(AddRecipeState())
    val state: StateFlow<AddRecipeState> = _state

    fun addIngredient() {
        _state.value = _state.value.copy(ingredients = _state.value.ingredients + IngredientField())
    }

    fun updateIngredientUnit(index: Int, unit: String) {
        val currentIngredients = _state.value.ingredients.toMutableList()
        if (index in currentIngredients.indices) {
            currentIngredients[index] = currentIngredients[index].copy(unit = unit)
            _state.value = _state.value.copy(ingredients = currentIngredients)
        }
    }

    fun addInstruction() {
        _state.value = _state.value.copy(instructions = _state.value.instructions + TextFieldState())
    }

    fun toggleCategory(category: String) {
        val currentCategories = _state.value.categories.toMutableList()
        if (currentCategories.contains(category)) {
            currentCategories.remove(category)
        } else {
            currentCategories.add(category)
        }
        _state.value = _state.value.copy(categories = currentCategories)
    }

    fun togglePublic() {
        _state.value = _state.value.copy(public = !_state.value.public)
    }

    fun toggleGlutenFree() {
        _state.value = _state.value.copy(glutenFree = !_state.value.glutenFree)
    }
}