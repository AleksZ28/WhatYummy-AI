package com.azurowski.whatyummyai.main.model

import com.google.firebase.Timestamp

data class Recipe(
    val id: String = "",
    val title: String = "",
    val ingredients: List<Ingredient> = emptyList(),
    val instructions: String = "",
    val isGlutenFree: Boolean = false,
    val public: Boolean = false,
    val authorId: String = "",
    val authorName: String = "",
    val imageUrl: String = "",
    val createdAt: Timestamp = Timestamp.now()
)

data class Ingredient(
    val amount: Double = 0.0,
    val ingredient: String = "",
    val unit: String = ""
)