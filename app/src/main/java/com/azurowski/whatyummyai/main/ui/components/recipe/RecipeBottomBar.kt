package com.azurowski.whatyummyai.main.ui.components.recipe

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.azurowski.whatyummyai.main.ui.theme.White50

@Composable
fun RecipeBottomBar(){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .padding(start = 24.dp, end = 24.dp, bottom = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = White50, shape = RoundedCornerShape(999.dp))
                .padding(horizontal = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Button(
                onClick = { },
                colors = ButtonDefaults.buttonColors(containerColor = White50, contentColor = Color.Black),
                modifier = Modifier
                    .weight(1f)
            ) {
                Text("Edytuj")
            }
            Button(
                onClick = { },
                colors = ButtonDefaults.buttonColors(containerColor = White50, contentColor = Color.Black),
                modifier = Modifier
                    .weight(1f)
            ) {
                Text("Usuń")
            }
        }
    }
}