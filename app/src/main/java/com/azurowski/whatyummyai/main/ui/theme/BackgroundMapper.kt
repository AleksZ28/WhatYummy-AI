package com.azurowski.whatyummyai.main.ui.theme

import androidx.compose.ui.graphics.Brush

fun BackgroundMapper(themeId: Int): Brush {
    return when(themeId) {
        1 -> Backgrounds.bg1
        2 -> Backgrounds.bg2
        3 -> Backgrounds.bg3
        else -> Backgrounds.bg1
    }
}