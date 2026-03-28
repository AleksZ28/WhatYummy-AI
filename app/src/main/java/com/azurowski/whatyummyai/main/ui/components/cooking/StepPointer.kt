package com.azurowski.whatyummyai.main.ui.components.cooking

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.unit.dp
import com.azurowski.whatyummyai.main.ui.theme.LightGreen50
import com.azurowski.whatyummyai.main.ui.theme.White50
import kotlinx.coroutines.Job

@Composable
fun StepPointer(
    stepIndex: Int,
    finished: Boolean = false,
    active: Boolean = false,
    onClick: () -> Job
){
    val targetSize = if (active) 36.dp else 32.dp
    val animatedSize by animateDpAsState(targetValue = targetSize)

    val targetColor = when {
        finished -> LightGreen50
        active -> Color.White
        else -> White50
    }
    val animatedColor by animateColorAsState(targetValue = targetColor)

    Box(
        modifier = Modifier
            .size(38.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .background(animatedColor, RoundedCornerShape(100))
                .size(animatedSize)
                .dropShadow(
                    shape = RoundedCornerShape(100),
                    shadow = Shadow(
                        radius = 2.dp,
                        color = if (active) Color.White else Color.Transparent,
                    )
                )
                .clickable(
                    onClick = { onClick() },
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = (stepIndex+1).toString(),
            )
        }
    }
}