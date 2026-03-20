package com.azurowski.whatyummyai.main.ui.components.recipe

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
import androidx.compose.runtime.MutableIntState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.azurowski.whatyummyai.main.ui.theme.White50

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
fun QuickInfoPanel(category: String, minutes: Int, portionCount: MutableIntState){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = White50, RoundedCornerShape(100))
            .padding(start = 20.dp, top = 10.dp, bottom = 10.dp, end = 14.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = category,
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        )

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