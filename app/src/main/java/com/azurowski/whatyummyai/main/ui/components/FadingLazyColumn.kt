package com.azurowski.whatyummyai.main.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.azurowski.whatyummyai.main.model.RecipeSummary

@Composable
fun FadingLazyColumn(navController: NavController, items: List<RecipeSummary>, themeId: Int){
    Box(
        modifier = Modifier
            .graphicsLayer {
                compositingStrategy = CompositingStrategy.Offscreen
            }
            .drawWithCache {
                val fadeWidth = 0.08f
                val fadeBrush = Brush.verticalGradient(
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
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(vertical = 20.dp)
        ) {
            items(items) {
                RecipeListItem(navController, it, themeId)
            }
        }
    }
}