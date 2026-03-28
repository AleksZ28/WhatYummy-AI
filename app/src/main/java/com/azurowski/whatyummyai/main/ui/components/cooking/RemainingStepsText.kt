package com.azurowski.whatyummyai.main.ui.components.cooking

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

@Composable
fun RemainingStepsText(remainingStepsCount: Int) {
    Text(
        text = "Pozostało kroków: ${remainingStepsCount}",
        textAlign = TextAlign.Center,
        style = TextStyle(
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp
        ),
        modifier = Modifier.fillMaxWidth()
    )
}