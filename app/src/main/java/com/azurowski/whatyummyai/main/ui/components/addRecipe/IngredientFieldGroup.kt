package com.azurowski.whatyummyai.main.ui.components.addRecipe

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.unit.dp
import com.azurowski.whatyummyai.main.model.IngredientField

@Composable
fun IngredientFieldGroup(
    ingredientsData: List<IngredientField>,
    onAddIngredient: () -> Unit,
    onUnitSelected: (Int, String) -> Unit
){
    val lastField = ingredientsData.last()

    LaunchedEffect(lastField) {
        snapshotFlow { lastField.name.text }.collect { text ->
            if (text.isNotEmpty()){
                onAddIngredient()
            }
        }
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        ingredientsData.forEachIndexed { index, data ->
            IngredientField(
                fieldData = data,
                onUnitSelected = { unit -> onUnitSelected(index, unit) }
            )
        }
    }
}