package com.azurowski.whatyummyai.main.ui.screens.home

import android.util.Log
import androidx.lifecycle.ViewModel
import com.azurowski.whatyummyai.main.model.RecipeSummary
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HomeViewModel : ViewModel() {
    private val db = Firebase.firestore

    private val _recipes = MutableStateFlow<List<RecipeSummary>>(emptyList())
    val recipes: StateFlow<List<RecipeSummary>> = _recipes

    private val _recentRecipes = MutableStateFlow<List<RecipeSummary>>(emptyList())
    val recentRecipes: StateFlow<List<RecipeSummary>> = _recentRecipes

    private var currentRecentIds: List<String> = emptyList()

    val user = FirebaseAuth.getInstance().currentUser

    fun setRecentRecipeIds(ids: List<String>) {
        currentRecentIds = ids
        updateRecentRecipes()
    }

    private fun updateRecentRecipes() {
        val allRecipes = _recipes.value
        if (allRecipes.isEmpty()) return
        
        _recentRecipes.value = currentRecentIds.mapNotNull { id ->
            allRecipes.find { it.id == id }
        }
    }

    fun fetchRecipes() {
        val userId = user?.uid
        Log.d("uid", userId.toString())
        db.collection("recipes")
            .where(
                Filter.or(
                    Filter.equalTo("authorId", userId),
                    Filter.equalTo("public", true)
                )
            )
            .get()
            .addOnSuccessListener { result ->
                val items = result.map { document ->
                    val ingredientsRaw = document.get("ingredients") as? List<Map<String, Any>> ?: emptyList()
                    val ingredientNames = ingredientsRaw.map { it["ingredient"] as? String ?: "" }

                    RecipeSummary(
                        id = document.id,
                        title = document.getString("title") ?: "",
                        isGlutenFree = document.getBoolean("isGlutenFree") ?: false,
                        ingredientNames = ingredientNames,
                        categories = document.get("categories") as? List<String> ?: emptyList()
                    )
                }
                _recipes.value = items
                updateRecentRecipes()
            }
    }
}