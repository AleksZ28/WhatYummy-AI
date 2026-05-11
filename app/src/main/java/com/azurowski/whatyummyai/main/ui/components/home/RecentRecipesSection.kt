package com.azurowski.whatyummyai.main.ui.components.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.azurowski.whatyummyai.main.model.RecipeSummary
import com.azurowski.whatyummyai.main.ui.components.RecipeListItem

@Composable
fun RecentRecipesSection(
    recipes: List<RecipeSummary>,
    onRecipeClick: (String, String) -> Unit
){
    Text(
        text = "Ostatnie przepisy",
        style = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
    )

    Column(
        modifier = Modifier
            .padding(vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        if (recipes.isNotEmpty()) {
            recipes.forEach { recipe ->
                RecipeListItem(recipe = recipe, onClick = onRecipeClick)
            }
        } else {
            Text(
                text = "Brak ostatnich przepisów",
                style = TextStyle(
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }

}