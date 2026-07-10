package com.azurowski.whatyummyai.main.ui.components.home

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AutoAwesome
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AIButton(modifier: Modifier, onClick: () -> Unit) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val scale by animateFloatAsState(if (isPressed) 0.95f else 1.0f, label = "buttonScale")
    val shadowElevation by animateFloatAsState(if (isPressed) 4f else 12f, label = "buttonShadow")

    val infiniteTransition = rememberInfiniteTransition(label = "pearlShimmerTransition")
    val progress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 5000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "shimmerProgress"
    )

    val pearlGradient = Brush.linearGradient(
        colors = listOf(
            Color(0xFFF8FAFC), // Pearl White Base
            Color(0xFFEEF2F6), // Platinum
            Color(0xFFE0E7FF), // Extremely Soft Lavender
            Color(0xFFFCE7F3), // Extremely Soft Pink
            Color(0xFFE0F2FE), // Extremely Soft Ice Blue
            Color(0xFFFEF3C7), // Extremely Soft Champagne/Gold
            Color(0xFFF8FAFC)  // Back to Pearl White
        ),

        start = Offset(x = progress * 400f - 200f, y = 0f),
        end = Offset(x = progress * 400f + 200f, y = 300f)
    )

    val borderBrush = Brush.verticalGradient(
        colors = listOf(
            Color.White.copy(alpha = 0.8f),
            Color.White.copy(alpha = 0.2f),
            Color.Transparent
        )
    )

    val contentColor = Color(0xFF312E81)

    val buttonHeight = 54.dp
    val cornerRadius = 27.dp

    Box(
        modifier = modifier
            .padding(end = 24.dp, bottom = 24.dp)
            .scale(scale)
            .shadow(
                elevation = shadowElevation.dp,
                shape = RoundedCornerShape(cornerRadius),
                clip = false,
                ambientColor = Color(0xFFC7D2FE),
                spotColor = Color(0xFFFBCFE8)
            )
            .background(brush = pearlGradient, shape = RoundedCornerShape(cornerRadius))
            .border(width = 1.dp, brush = borderBrush, shape = RoundedCornerShape(cornerRadius))
            .clip(RoundedCornerShape(cornerRadius))
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            )
            .height(buttonHeight)
            .padding(horizontal = 24.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Rounded.AutoAwesome,
                contentDescription = "AI Icon",
                tint = contentColor,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = "AI",
                color = contentColor,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.5.sp
            )
        }
    }
}