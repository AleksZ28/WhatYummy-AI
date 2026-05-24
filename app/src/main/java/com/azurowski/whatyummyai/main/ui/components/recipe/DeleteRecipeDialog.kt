package com.azurowski.whatyummyai.main.ui.components.recipe

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun DeleteRecipeDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text(text = "Usuń przepis") },
            text = { Text(text = "Czy na pewno chcesz usunąć ten przepis?") },
            confirmButton = {
                TextButton(
                    onClick = onConfirm
                ) {
                    Text("Usuń", color = Color.Red)
                }
            },
            dismissButton = {
                TextButton(onClick = onDismiss) {
                    Text("Anuluj", color = Color.Black)
                }
            }
        )
    }
}
