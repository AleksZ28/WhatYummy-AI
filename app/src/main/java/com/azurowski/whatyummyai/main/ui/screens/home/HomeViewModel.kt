package com.azurowski.whatyummyai.main.ui.screens.home

import androidx.lifecycle.ViewModel
import com.azurowski.whatyummyai.main.model.RecipeSummary
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HomeViewModel : ViewModel() {
    private val db = Firebase.firestore

    private val _recipes = MutableStateFlow<List<RecipeSummary>>(emptyList())
    val recipes: StateFlow<List<RecipeSummary>> = _recipes

    fun fetchRecipes() {
        db.collection("recipes")
            .whereEqualTo("public", true)
            .get()
            .addOnSuccessListener { result ->
                val items = result.map { document ->
                    RecipeSummary(
                        id = document.id,
                        title = document.getString("title") ?: "",
                        isGlutenFree = document.getBoolean("isGlutenFree") ?: false,
                    )
                }
                _recipes.value = items
            }
    }
}