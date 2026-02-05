package com.azurowski.whatyummyai.main.ui.components.addRecipe

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.azurowski.whatyummyai.main.ui.theme.White50

@Composable
fun AddRecipeTextField(state: TextFieldState, placeholder: String, lineLimits: TextFieldLineLimits, keyboardOptions: KeyboardOptions = KeyboardOptions()){
    TextField(
        modifier = Modifier.fillMaxWidth(),
        state = state,
        placeholder = { Text(placeholder) },
        shape = RoundedCornerShape(8.dp),
        lineLimits = lineLimits,
        keyboardOptions = keyboardOptions,
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Gray,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            errorIndicatorColor = Color.Transparent,
            focusedContainerColor = White50,
            unfocusedContainerColor = White50,
            disabledContainerColor = White50,
            errorContainerColor = White50
        )
    )
}