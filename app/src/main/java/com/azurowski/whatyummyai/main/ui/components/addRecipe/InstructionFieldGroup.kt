package com.azurowski.whatyummyai.main.ui.components.addRecipe

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp

@Composable
fun InstructionFieldGroup(instructions: List<TextFieldState>, onAddInstruction: () -> Unit){
    val lastField = instructions.last()

    LaunchedEffect(lastField) {
        snapshotFlow { lastField.text }.collect { text ->
            if (text.isNotEmpty()) {
                onAddInstruction()
            }
        }
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        instructions.forEachIndexed { index, state ->
            AddRecipeTextField(
                state = state,
                placeholder = "Krok ${index + 1}",
                lineLimits = TextFieldLineLimits.SingleLine,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    capitalization = KeyboardCapitalization.Sentences
                )
            )
        }
    }
}