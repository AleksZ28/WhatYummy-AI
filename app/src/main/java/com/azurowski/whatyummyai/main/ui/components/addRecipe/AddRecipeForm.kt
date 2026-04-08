package com.azurowski.whatyummyai.main.ui.components.addRecipe

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.azurowski.whatyummyai.main.model.IngredientField
import com.azurowski.whatyummyai.main.ui.components.FadingBoxVertical

@Composable
fun AddRecipeForm(ingredientsData: List<IngredientField>, instructions: List<TextFieldState>, onAddIngredient: () -> Unit, onAddInstruction: () -> Unit){
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

                Text(
                    text = "Składniki",
                    modifier = Modifier.padding(top = 8.dp).fillMaxWidth(),
                    style = TextStyle(
                        textAlign = TextAlign.Center,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium
                    )
                )

                IngredientFieldGroup(ingredientsData, onAddIngredient)

                Text(
                    text = "Instrukcje",
                    modifier = Modifier.padding(top = 8.dp).fillMaxWidth(),
                    style = TextStyle(
                        textAlign = TextAlign.Center,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium
                    )
                )

                InstructionFieldGroup(instructions, onAddInstruction)

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