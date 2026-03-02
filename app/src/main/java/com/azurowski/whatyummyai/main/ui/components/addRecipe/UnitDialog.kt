package com.azurowski.whatyummyai.main.ui.components.addRecipe

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.azurowski.whatyummyai.main.ui.theme.GrayStatic

@Composable
fun UnitDialog(
    onDismissRequest: () -> Unit,
    dialogTitle: String,
    units: List<String>,
    selectUnit: (unit: String) -> Unit
) {
    AlertDialog(
        title = {
            Text(text = dialogTitle)
        },
        text = {
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState())
            ) {
                units.forEach { unit ->
                    TextButton(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { selectUnit(unit) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent,
                            contentColor = Color.Black
                        )
                    ) {
                        Text(text = unit)
                    }
                }
            }
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {},
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                },
                colors = ButtonDefaults.textButtonColors(
                    contentColor = Color.Black
                )
            ) {
                Text("Anuluj")
            }
        },
        containerColor = GrayStatic,
    )
}