package com.azurowski.whatyummyai.main.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.Exclude

data class Recipe(
    @get:Exclude val id: String = "",
    val title: String = "",
    val ingredients: List<Ingredient> = emptyList(),
    val instructions: List<String> = emptyList(),
    val categories: List<String> = emptyList(),
    val totalMinutes: Int = 0,
    val isGlutenFree: Boolean = false,
    val public: Boolean = false,
    val authorId: String = "",
    val authorName: String = "",
    val imageUrls: List<String> = emptyList(),
    val createdAt: Timestamp = Timestamp.now()
)

data class RecipeSummary(
    val id: String = "",
    val title: String = "",
    val isGlutenFree: Boolean = false,
)

data class Ingredient(
    val amount: Double = 0.0,
    val ingredient: String = "",
    val unit: String = ""
)