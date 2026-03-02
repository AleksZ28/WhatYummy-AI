package com.azurowski.whatyummyai.main.model

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class IngredientField(
    val name: TextFieldState = TextFieldState(),
    val amount: TextFieldState = TextFieldState(),
    val unit: MutableState<String> = mutableStateOf("")
)