package com.azurowski.whatyummyai.main.ui.theme

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

object Backgrounds {
    val bg1 = Brush.linearGradient(
        colors = listOf(
            Color(0xFFFFA01C),
            Color(0xFFF3E097)
        ),
        start = Offset(Float.POSITIVE_INFINITY, 0f),
        end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
    )

    val bg2 = Brush.linearGradient(
        colors = listOf(
            Color(0xFFF88C7F),
            Color(0xFFDBD4B4)
        ),
        start = Offset(Float.POSITIVE_INFINITY, 0f),
        end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
    )

    val bg3 = Brush.linearGradient(
        colors = listOf(
            Color(0xFFA11000),
            Color(0xFF8BB1DF)
        ),
        start = Offset(Float.POSITIVE_INFINITY, 0f),
        end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
    )
}