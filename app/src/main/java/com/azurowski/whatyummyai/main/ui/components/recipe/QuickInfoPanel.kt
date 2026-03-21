package com.azurowski.whatyummyai.main.ui.components.recipe

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.azurowski.whatyummyai.capitalizeFirst
import com.azurowski.whatyummyai.main.ui.theme.White50
import kotlinx.coroutines.delay

@Composable
fun PortionButton(increment: Boolean, onClick: () -> Unit){
    IconButton(
        onClick = onClick,
        modifier = Modifier
            .background(color = White50, RoundedCornerShape(100))
            .size(40.dp)
    ) {
        Icon(imageVector = if (increment) Icons.Default.Add else Icons.Default.Remove, contentDescription = null)
    }
}

@Composable
fun QuickInfoPanel(categories: List<String>, minutes: Int, portionCount: MutableIntState){

    var currentCategoryIndex by remember { mutableIntStateOf(0) }

    LaunchedEffect(Unit) {
        if (categories.size > 1) {
            while (true) {
                delay(2000)
                currentCategoryIndex = (currentCategoryIndex + 1) % categories.size
            }
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = White50, RoundedCornerShape(100))
            .padding(start = 20.dp, top = 10.dp, bottom = 10.dp, end = 14.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        AnimatedContent(
            targetState = currentCategoryIndex,
            label = "animated content"
        ) { targetIndex ->
            Text(
                text = categories[targetIndex].capitalizeFirst(),
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            )
        }

        Text(
            text = "$minutes min",
            style = TextStyle(
                fontSize = 14.sp
            )
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            PortionButton(false, { if (portionCount.intValue != 1) portionCount.intValue-- })
            Text(
                text = portionCount.intValue.toString(),
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            )
            PortionButton(true, { if (portionCount.intValue != 30) portionCount.intValue++ })
        }
    }
}