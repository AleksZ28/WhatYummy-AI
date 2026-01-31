package com.azurowski.whatyummyai.main.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.azurowski.whatyummyai.R
import com.azurowski.whatyummyai.main.ui.theme.White50

@Composable
fun RecipeListItem(title: String, glutenFree: Boolean){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(color = White50)
            .padding(start = 16.dp)
            .height(48.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = TextStyle(
                fontWeight = FontWeight.Medium
            )
        )
        if (glutenFree) {
            Box(
                modifier = Modifier
                    .background(color = Color(165,255,86,175))
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ){
                Image(
                    painter = painterResource(R.drawable.ic_gluten),
                    contentDescription = null,
                    modifier = Modifier
                        .size(36.dp)

                )
            }
        }

    }
}