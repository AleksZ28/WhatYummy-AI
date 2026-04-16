package com.azurowski.whatyummyai.main.ui.components.addRecipe

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.azurowski.whatyummyai.main.model.IngredientField
import com.azurowski.whatyummyai.main.ui.components.FadingBoxVertical
import com.azurowski.whatyummyai.main.ui.theme.White50

@Composable
fun AddRecipeForm(
    title: TextFieldState,
    totalMinutes: TextFieldState,
    timeUnit: String,
    ingredientsData: List<IngredientField>,
    instructions: List<TextFieldState>,
    selectedCategories: List<String>,
    isPublic: Boolean,
    isGlutenFree: Boolean,
    onAddIngredient: () -> Unit,
    onUnitSelected: (Int, String) -> Unit,
    onTimeUnitSelected: (String) -> Unit,
    onAddInstruction: () -> Unit,
    onCategoryToggle: (String) -> Unit,
    onPublicToggle: () -> Unit,
    onGlutenFreeToggle: () -> Unit
){
    val openTimeDialog = remember { mutableStateOf(false) }
    val timeUnits = listOf("min", "h")

    if (openTimeDialog.value) {
        UnitDialog(
            onDismissRequest = { openTimeDialog.value = false },
            dialogTitle = "Wybierz jednostkę czasu",
            units = timeUnits,
            selectUnit = {
                onTimeUnitSelected(it)
                openTimeDialog.value = false
            }
        )
    }

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
                    state = title,
                    placeholder = "Nazwa przepisu",
                    lineLimits = TextFieldLineLimits.SingleLine,
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    AddRecipeTextField(
                        modifier = Modifier.weight(1f),
                        state = totalMinutes,
                        placeholder = "Czas przygotowania",
                        lineLimits = TextFieldLineLimits.SingleLine,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )

                    Button(
                        modifier = Modifier
                            .weight(.4f)
                            .height(56.dp),
                        onClick = { openTimeDialog.value = true },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = White50,
                            contentColor = Color.Black
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = timeUnit,
                            textAlign = TextAlign.Center
                        )
                    }
                }

                Text(
                    text = "Składniki",
                    modifier = Modifier.padding(top = 8.dp).fillMaxWidth(),
                    style = TextStyle(
                        textAlign = TextAlign.Center,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium
                    )
                )

                IngredientFieldGroup(
                    ingredientsData = ingredientsData,
                    onAddIngredient = onAddIngredient,
                    onUnitSelected = onUnitSelected
                )

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
            CategoriesSelection(
                selectedCategories = selectedCategories,
                onCategoryToggle = onCategoryToggle
            )

            Options(
                isPublic = isPublic,
                isGlutenFree = isGlutenFree,
                onPublicToggle = onPublicToggle,
                onGlutenFreeToggle = onGlutenFreeToggle
            )
        }
    }
}