package com.azurowski.whatyummyai.main.ui.components.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.azurowski.whatyummyai.main.ui.theme.GrayStatic

@Composable
fun AddToKitchenButton(modifier: Modifier) {
    ExtendedFloatingActionButton(
        text = { Text(text = "Dodaj do kuchni") },
        icon = { Icon(Icons.Filled.Add, contentDescription = "Dodaj do kuchni") },
        onClick = { },
        containerColor = GrayStatic,
        modifier = modifier.padding(end = 24.dp, bottom = 24.dp)
    )
}