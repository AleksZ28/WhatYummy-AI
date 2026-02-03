package com.azurowski.whatyummyai.main.ui.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun AddKitchenItemDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
){
    if (showDialog) {
        val newItemFieldState = rememberTextFieldState()

        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text(text = "Dodaj do kuchni") },
            text = {
                TextField(
                    state = newItemFieldState,
                    placeholder = { Text("Nazwa produktu") },
                    shape = RoundedCornerShape(100),
                    lineLimits = TextFieldLineLimits.SingleLine,
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        errorIndicatorColor = Color.Transparent
                    )
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    val currentText = newItemFieldState.text.toString()
                    if (currentText.isNotEmpty()) {
                        onConfirm(currentText)
                        newItemFieldState.edit { replace(0, length, "") }
                    }
                }) { Text("Dodaj") }
            },
            dismissButton = {
                TextButton(onClick = {
                    newItemFieldState.edit { replace(0, length, "") }
                    onDismiss()
                }) { Text("Anuluj") }
            },
        )
    }
}