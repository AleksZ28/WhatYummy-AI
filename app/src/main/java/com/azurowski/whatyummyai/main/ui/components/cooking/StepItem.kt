package com.azurowski.whatyummyai.main.ui.components.cooking

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.azurowski.whatyummyai.main.ui.components.FadingBoxVertical
import com.azurowski.whatyummyai.main.ui.theme.GrayText

@Composable
fun StepItem(stepIndex: Int, instruction: String) {
    FadingBoxVertical() {
        Column(
            modifier = Modifier.padding(horizontal = 24.dp).verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = (stepIndex+1).toString(),
                color = GrayText,
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 64.sp
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = instruction,
                textAlign = TextAlign.Center,
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 54.sp
                )
            )
            Spacer(modifier = Modifier.height(12.dp))
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Preview
@Composable
fun StepItemPreview(){
    StepItem(2, "Łączymy wodę i drożdże")
}