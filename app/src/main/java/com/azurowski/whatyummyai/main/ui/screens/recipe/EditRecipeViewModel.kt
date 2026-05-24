package com.azurowski.whatyummyai.main.ui.screens.recipe

import android.util.Log
import androidx.compose.foundation.text.input.TextFieldState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.azurowski.whatyummyai.main.model.EditRecipeState
import com.azurowski.whatyummyai.main.model.IngredientField
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class EditRecipeViewModel : ViewModel() {
    private val db = Firebase.firestore
    private val _state = MutableStateFlow(EditRecipeState())
    val state: StateFlow<EditRecipeState> = _state

    fun fetchRecipe(recipeId: String) {
        db.collection("recipes").document(recipeId)
            .get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val ingredientsRaw = document.get("ingredients") as? List<Map<String, Any>> ?: emptyList()

                    val ingredients = ingredientsRaw.map {
                        IngredientField(
                            name = TextFieldState(it["ingredient"] as? String ?: ""),
                            amount = TextFieldState((it["amount"] as? Number)?.toString() ?: ""),
                            unit = it["unit"] as? String ?: ""
                        )
                    }

                    val instructionsRaw = document.get("instructions") as? List<String> ?: emptyList()
                    val instructions = instructionsRaw.map { TextFieldState(it) }

                    val totalMinutes = document.getLong("totalMinutes")?.toInt() ?: 0
                    val (time, unit) = if (totalMinutes % 60 == 0 && totalMinutes > 0) {
                        (totalMinutes / 60) to "h"
                    } else {
                        totalMinutes to "min"
                    }

                    _state.value = EditRecipeState(
                        title = TextFieldState(document.getString("title") ?: ""),
                        totalTime = TextFieldState(time.toString()),
                        timeUnit = unit,
                        ingredients = ingredients,
                        instructions = instructions,
                        categories = document.get("categories") as? List<String> ?: emptyList(),
                        glutenFree = document.getBoolean("isGlutenFree") ?: false,
                        public = document.getBoolean("public") ?: false,
                        images = document.get("imageUrls") as? List<String> ?: emptyList()
                    )
                }
            }
    }

    fun updateRecipe(recipeId: String, onSuccess: () -> Unit) {
        if (_state.value.isSaving) return

        val titleVal = _state.value.title.text.toString().trim()
        val timeVal = _state.value.totalTime.text.toString().trim()
        val hasIngredients = _state.value.ingredients.any { it.name.text.toString().isNotBlank() }
        val hasInstructions = _state.value.instructions.any { it.text.toString().isNotBlank() }

        if (titleVal.isEmpty() || timeVal.isEmpty() || !hasIngredients || !hasInstructions) return

        viewModelScope.launch {
            _state.value = _state.value.copy(isSaving = true)

            try {
                val timeMultiplier = if (_state.value.timeUnit == "h") 60 else 1

                val updatedData = mapOf(
                    "title" to _state.value.title.text.toString(),
                    "ingredients" to _state.value.ingredients.map {
                        mapOf(
                            "ingredient" to it.name.text.toString(),
                            "amount" to (it.amount.text.toString().replace(",", ".").replace(" ", "").toDoubleOrNull() ?: 0.0),
                            "unit" to it.unit
                        )
                    }.filter { (it["ingredient"] as String).isNotEmpty() },
                    "instructions" to _state.value.instructions.map { it.text.toString() }
                        .filter { it.isNotEmpty() },
                    "categories" to _state.value.categories,
                    "totalMinutes" to (_state.value.totalTime.text.toString().toIntOrNull()
                        ?: 0) * timeMultiplier,
                    "isGlutenFree" to _state.value.glutenFree,
                    "public" to _state.value.public
                )

                db.collection("recipes").document(recipeId)
                    .update(updatedData)
                    .addOnSuccessListener {
                        _state.value = _state.value.copy(isSaving = false)
                        onSuccess()
                    }
                    .addOnFailureListener { e ->
                        Log.e("EditRecipe", e.toString())
                        _state.value = _state.value.copy(isSaving = false)
                    }

            } catch (e: Exception) {
                Log.e("EditRecipe", e.toString())
                _state.value = _state.value.copy(isSaving = false)
            }
        }
    }

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

    fun updateTimeUnit(unit: String) {
        _state.value = _state.value.copy(timeUnit = unit)
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