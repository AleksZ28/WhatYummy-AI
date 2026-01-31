package com.azurowski.whatyummyai.main.ui.screens.home

import androidx.lifecycle.ViewModel
import com.azurowski.whatyummyai.main.model.Recipe
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HomeViewModel : ViewModel() {
    private val db = Firebase.firestore

    private val _recipes = MutableStateFlow<List<Recipe>>(emptyList())
    val recipes: StateFlow<List<Recipe>> = _recipes

    fun fetchRecipes() {
        db.collection("recipes")
            .whereEqualTo("public", true)
            .get()
            .addOnSuccessListener { result ->
                val items = result?.toObjects(Recipe::class.java) ?: emptyList()
                _recipes.value = items
            }
    }
}