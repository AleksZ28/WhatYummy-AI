package com.azurowski.whatyummyai.main.ui.components.addRecipe

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun AddRecipeForm(){
    Column(
        modifier = Modifier
            .padding(vertical = 12.dp, horizontal = 30.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ){
        AddRecipeTextField(
            state = rememberTextFieldState(),
            placeholder = "Nazwa przepisu",
            lineLimits = TextFieldLineLimits.SingleLine
        )

        IngredientFieldGroup()

        AddRecipeTextField(
            state = rememberTextFieldState(),
            placeholder = "Instrukcje",
            lineLimits = TextFieldLineLimits.MultiLine(minHeightInLines = 5),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Default,
                capitalization = KeyboardCapitalization.Sentences
            )
        )

        CategoriesSelection()

        Options()

    }
}