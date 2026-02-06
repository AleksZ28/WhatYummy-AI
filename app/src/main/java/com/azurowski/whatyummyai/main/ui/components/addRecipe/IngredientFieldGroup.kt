package com.azurowski.whatyummyai.main.ui.components.addRecipe

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.unit.dp

@Composable
fun IngredientFieldGroup(){
    val ingredientsStates = remember { mutableStateListOf(TextFieldState()) }

    val lastFieldState by remember { derivedStateOf { ingredientsStates.last() } }

    LaunchedEffect(lastFieldState) {
        snapshotFlow { lastFieldState.text }.collect { text ->
            if (text.isNotEmpty()){
                ingredientsStates.add(TextFieldState())
            }
        }
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ingredientsStates.forEach{ state ->
            AddRecipeTextField(
                state = state,
                placeholder = "Składnik",
                lineLimits = TextFieldLineLimits.SingleLine
            )
        }
    }
}