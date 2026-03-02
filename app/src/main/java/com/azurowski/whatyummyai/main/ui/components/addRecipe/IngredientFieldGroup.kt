package com.azurowski.whatyummyai.main.ui.components.addRecipe

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.unit.dp
import com.azurowski.whatyummyai.main.model.IngredientField

@Composable
fun IngredientFieldGroup(ingredientsData: SnapshotStateList<IngredientField>){
    
    val lastFieldState by remember { derivedStateOf { ingredientsData.last() } }

    LaunchedEffect(lastFieldState) {
        snapshotFlow { lastFieldState.name.text }.collect { text ->
            if (text.isNotEmpty()){
                ingredientsData.add(IngredientField())
            }
        }
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ingredientsData.forEach{ data ->
            IngredientField(data)
        }
    }
}