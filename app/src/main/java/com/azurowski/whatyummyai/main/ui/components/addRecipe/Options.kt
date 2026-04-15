package com.azurowski.whatyummyai.main.ui.components.addRecipe

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MultiChoiceSegmentedButtonRow
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.azurowski.whatyummyai.main.ui.theme.White50

@Composable
fun Options(
    isPublic: Boolean,
    isGlutenFree: Boolean,
    onPublicToggle: () -> Unit,
    onGlutenFreeToggle: () -> Unit
) {
    val options = listOf("Private", "GlutenFree")

    MultiChoiceSegmentedButtonRow(
        modifier = Modifier.fillMaxWidth()
    ) {
        options.forEachIndexed { index, label ->
            val isChecked = when (label) {
                "Private" -> !isPublic
                "GlutenFree" -> isGlutenFree
                else -> false
            }

            SegmentedButton(
                shape = SegmentedButtonDefaults.itemShape(
                    index = index,
                    count = options.size
                ),
                checked = isChecked,
                onCheckedChange = {
                    when (label) {
                        "Private" -> onPublicToggle()
                        "GlutenFree" -> onGlutenFreeToggle()
                    }
                },
                icon = { SegmentedButtonDefaults.Icon(isChecked) },
                label = {
                    when (label) {
                        "Private" -> Text("Prywatny")
                        "GlutenFree" -> Text("Bezglutenowe")
                    }
                },
                colors = SegmentedButtonDefaults.colors(activeContainerColor = White50)
            )
        }
    }
}