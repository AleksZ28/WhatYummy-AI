package com.azurowski.whatyummyai.main.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.azurowski.whatyummyai.main.model.RecipeSummary

@Composable
fun FadingLazyColumn(items: List<RecipeSummary>, onRecipeClick: (String, String) -> Unit){
    FadingBoxVertical(modifier = Modifier) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(vertical = 20.dp)
        ) {
            items(items) {
                RecipeListItem(it, onRecipeClick)
            }
        }
    }
}