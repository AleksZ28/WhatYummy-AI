package com.azurowski.whatyummyai.main.ui.components.addRecipe

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.azurowski.whatyummyai.main.model.IngredientField
import com.azurowski.whatyummyai.main.ui.theme.White50

@Composable
fun IngredientField(fieldData: IngredientField) {
    val openUnitDialog = remember { mutableStateOf(false) }
    val units = listOf("szt", "g", "kg", "mg", "ml", "l", "szczypty", "łyżeczki", "łyżki", "szklanki")

    fun selectUnit(unit: String) {
        fieldData.unit.value = unit
        openUnitDialog.value = false
    }

    if (openUnitDialog.value) {
        UnitDialog(
            onDismissRequest = { openUnitDialog.value = false },
            dialogTitle = "Wybierz jednostkę",
            units = units,
            selectUnit = { selectUnit(it) }
        )
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        AddRecipeTextField(
            state = fieldData.name,
            placeholder = "Składnik",
            lineLimits = TextFieldLineLimits.SingleLine,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
                capitalization = KeyboardCapitalization.Sentences
            )
        )

        Row(
            modifier = Modifier.padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ){
            TextField(
                modifier = Modifier.weight(1f),
                state = fieldData.amount,
                placeholder = { Text("Ilość") },
                shape = RoundedCornerShape(8.dp),
                lineLimits = TextFieldLineLimits.SingleLine,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Gray,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    errorIndicatorColor = Color.Transparent,
                    focusedContainerColor = White50,
                    unfocusedContainerColor = White50,
                    disabledContainerColor = White50,
                    errorContainerColor = White50
                )
            )

            Button(
                modifier = Modifier.weight(1f).height(56.dp),
                onClick = { openUnitDialog.value = true },
                colors = ButtonDefaults.buttonColors(
                    containerColor = White50,
                    contentColor = Color.Black
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = fieldData.unit.value.ifEmpty { "Wybierz jednostkę" },
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}