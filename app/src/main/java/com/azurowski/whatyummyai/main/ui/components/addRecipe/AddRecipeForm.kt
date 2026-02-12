package com.azurowski.whatyummyai.main.ui.components.addRecipe

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import com.azurowski.whatyummyai.main.ui.components.FadingBoxVertical

@Composable
fun AddRecipeForm(){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 30.dp, end = 30.dp, bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ){
        FadingBoxVertical(
            modifier = Modifier
                .weight(1f)
        ){
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                Spacer(modifier = Modifier.height(12.dp))

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

                Spacer(modifier = Modifier.height(16.dp))
            }
        }


        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            CategoriesSelection()

            Options()
        }
    }
}