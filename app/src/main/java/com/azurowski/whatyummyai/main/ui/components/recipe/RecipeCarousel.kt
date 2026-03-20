package com.azurowski.whatyummyai.main.ui.components.recipe

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.carousel.HorizontalMultiBrowseCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage

@Composable
fun RecipeCarousel(imageUrls: List<String>) {
    HorizontalMultiBrowseCarousel(
        state = rememberCarouselState { imageUrls.size },
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        preferredItemWidth = 250.dp,
        itemSpacing = 8.dp,
    ) { i ->
        val url = imageUrls[i]
        AsyncImage(
            modifier = Modifier
                .height(205.dp)
                .maskClip(MaterialTheme.shapes.extraLarge),
            model = url,
            contentDescription = "Zdjęcie $i",
            contentScale = ContentScale.Crop
        )
    }
}