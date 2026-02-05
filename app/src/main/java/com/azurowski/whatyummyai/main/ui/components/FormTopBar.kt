package com.azurowski.whatyummyai.main.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.azurowski.whatyummyai.main.ui.theme.White50

@Composable
fun FormTopBar(title: String, navigateBack: () -> Unit){
    Row(
        modifier = Modifier.padding(vertical = 12.dp, horizontal = 24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = navigateBack,
            modifier = Modifier.background(White50, RoundedCornerShape(100))
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Zamknij",
            )
        }

        Text(
            text = title,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center,
            style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 24.sp)
        )

        Spacer(modifier = Modifier.size(48.dp))

    }
}