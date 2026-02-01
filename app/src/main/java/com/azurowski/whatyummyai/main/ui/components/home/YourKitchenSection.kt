package com.azurowski.whatyummyai.main.ui.components.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.azurowski.whatyummyai.main.model.RecipeSummary
import com.azurowski.whatyummyai.main.ui.components.FadingLazyColumn

@Composable
fun YourKitchenSection(navController: NavController, recipes: List<RecipeSummary>, themeId: Int){
    Text(
        text = "Twoja kuchnia",
        style = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp
        )
    )

    Spacer(modifier = Modifier.height(12.dp))

    Row(
        modifier = Modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        KitchenProductItem("Produkt 1")
        KitchenProductItem("Produkt 2")
    }

    Spacer(modifier = Modifier.height(16.dp))

    Text(
        text = "Dostępne przepisy",
        style = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
    )

    FadingLazyColumn(navController, recipes, themeId)
}