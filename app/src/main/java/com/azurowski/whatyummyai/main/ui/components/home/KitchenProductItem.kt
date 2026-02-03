package com.azurowski.whatyummyai.main.ui.components.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.azurowski.whatyummyai.main.model.KitchenItem
import com.azurowski.whatyummyai.main.ui.theme.White50

@Composable
fun KitchenProductItem(kitchenItem: KitchenItem, onDelete: () -> Unit){
    Row(
        modifier = Modifier
            .background(color = White50, RoundedCornerShape(100.dp))
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = kitchenItem.name,
            style = TextStyle(
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium
            )
        )
        Icon(
            imageVector = Icons.Default.Clear,
            contentDescription = "Usuń",
            modifier = Modifier
                .height(12.dp)
                .padding(start = 8.dp)
                .clickable(onClick = {
                    onDelete()
                })
        )
    }
}