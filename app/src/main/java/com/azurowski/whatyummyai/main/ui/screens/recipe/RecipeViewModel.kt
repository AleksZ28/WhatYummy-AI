package com.azurowski.whatyummyai.main.ui.screens.recipe

import android.util.Log
import androidx.lifecycle.ViewModel
import com.azurowski.whatyummyai.main.model.Ingredient
import com.azurowski.whatyummyai.main.model.Recipe
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class RecipeViewModel : ViewModel() {
    private val db = Firebase.firestore

    private val _recipe = MutableStateFlow<Recipe>(Recipe())
    val recipe: StateFlow<Recipe> = _recipe

    fun fetchRecipe(recipeId: String) {
        db.collection("recipes").document(recipeId)
            .get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val recipe = Recipe(
                        id = document.id,
                        title = document.getString("title") ?: "",
                        ingredients = document.get("ingredients") as? List<Ingredient> ?: emptyList(),
                        instructions = document.get("instructions") as? List<String> ?: emptyList(),
                        categories = document.get("categories") as? List<String> ?: emptyList(),
                        totalMinutes = document.getLong("totalMinutes")?.toInt() ?: 0,
                        isGlutenFree = document.getBoolean("isGlutenFree") ?: false,
                        public = document.getBoolean("public") ?: false,
                        authorId = document.getString("authorId") ?: "",
                        authorName = document.getString("authorName") ?: "",
                        imageUrl = document.getString("imageUrl") ?: "",
                        createdAt = document.getTimestamp("createdAt") ?: Timestamp.now()
                    )

                    Log.d("Recipe", recipe.toString())

                    _recipe.value = recipe
                } else {
                    Log.d("Recipe", "Recipe not found")
                }
            }
    }
}