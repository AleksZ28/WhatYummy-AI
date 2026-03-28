package com.azurowski.whatyummyai.main.ui.components.cooking

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun StepsIndicatorRow(listState: LazyListState, pagerState: PagerState) {
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(pagerState.currentPage) {
        listState.animateScrollToItem(
            index = pagerState.currentPage,
            scrollOffset = -200
        )
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp, vertical = 12.dp)
            .graphicsLayer {
                compositingStrategy = CompositingStrategy.Offscreen
            }
            .drawWithCache {
                val fadeWidth = 0.1f
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
    ) {
        LazyRow(
            state = listState,
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            contentPadding = PaddingValues(horizontal = 24.dp)
        ) {
            items(pagerState.pageCount) { iteration ->
                StepPointer(
                    stepIndex = iteration,
                    active = pagerState.currentPage == iteration,
                    finished = pagerState.currentPage > iteration,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(iteration)
                        }
                    })
            }
        }
    }
}