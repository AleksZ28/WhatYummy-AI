package com.azurowski.whatyummyai.main.ui.screens.cooking

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.azurowski.whatyummyai.main.ui.theme.White50

@Composable
fun CookingScreen(
    navController: NavController,
    recipeId: String
){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(White50)
    ) {
        Text(
            text = "Przepis",
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(30.dp),
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )
        )
    }

}