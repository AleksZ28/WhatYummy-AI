package com.azurowski.whatyummyai.main.ui.sheets.cooking

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.RecordVoiceOver
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.azurowski.whatyummyai.main.ui.theme.AlmostBlack

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IntroBottomSheet(
    state: SheetState,
    onDismiss: () -> Unit
) {

    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        sheetState = state,
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Witaj w trybie gotowania!",
                style = TextStyle(
                    fontWeight = FontWeight.Medium,
                    fontSize = 24.sp,
                )
            )

            Icon(
                imageVector = Icons.Default.RecordVoiceOver,
                contentDescription = null,
                modifier = Modifier.size(100.dp)
            )

            Text(
                text = "Nie musisz dotykać ekranu brudnymi rękami. Używaj komend głosowych, aby zmieniać kroki:",
                style = TextStyle(
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center
                )
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(
                    text = "„Dalej”, „Następny”, „Następna”",
                    style = TextStyle(
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp,
                        fontStyle = FontStyle.Italic
                    )
                )

                Text(
                    text = "„Poprzedni”, „Poprzednia”, „Wróć”",
                    style = TextStyle(
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp,
                        fontStyle = FontStyle.Italic
                    )
                )

                Text(
                    text = "„Powtórz”, „Jeszcze raz”",
                    style = TextStyle(
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp,
                        fontStyle = FontStyle.Italic
                    )
                )
            }

            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(containerColor = AlmostBlack)
            ) {
                Text("Ok, nie pokazuj ponownie")
            }
        }
    }
}