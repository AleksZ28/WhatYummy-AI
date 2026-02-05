package com.azurowski.whatyummyai.main.ui.components.home

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.azurowski.whatyummyai.main.model.KitchenItem
import com.azurowski.whatyummyai.main.model.RecipeSummary
import com.azurowski.whatyummyai.main.ui.components.FadingLazyColumn

@Composable
fun YourKitchenSection(kitchenItems: List<KitchenItem>, onDelete: (String) -> Unit, recipes: List<RecipeSummary>, onRecipeClick: (String, String) -> Unit){
    Text(
        text = "Twoja kuchnia",
        style = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp
        )
    )

    Spacer(modifier = Modifier.height(12.dp))

    Box(
        modifier = Modifier
            .graphicsLayer {
                compositingStrategy = CompositingStrategy.Offscreen
            }
            .drawWithCache {
                val fadeWidth = 0.03f
                val fadeBrush = Brush.horizontalGradient(
                    0.0f to Color.Transparent,
                    fadeWidth to Color.Black,
                    (1.0f - fadeWidth) to Color.Black,
                    1.0f to Color.Transparent
                )

                onDrawWithContent {
                    drawContent()
                    drawRect(
                        brush = fadeBrush,
                        blendMode = BlendMode.DstIn
                    )
                }
            }
    ){
        Row(
            modifier = Modifier.horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Spacer(modifier = Modifier.width(1.dp))

            kitchenItems.forEach { item ->
                KitchenProductItem(kitchenItem = item, onDelete = { onDelete(item.id) })
            }

            Spacer(modifier = Modifier.width(1.dp))
        }
    }

    Spacer(modifier = Modifier.height(16.dp))

    Text(
        text = "Dostępne przepisy",
        style = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
    )

    FadingLazyColumn(recipes, onRecipeClick)
}