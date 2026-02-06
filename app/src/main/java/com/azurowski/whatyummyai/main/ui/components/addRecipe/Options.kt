package com.azurowski.whatyummyai.main.ui.components.addRecipe

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MultiChoiceSegmentedButtonRow
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.azurowski.whatyummyai.main.ui.theme.White50

@Composable
fun Options(){
    val options = listOf("Private", "GlutenFree")
    val selectedOptions = remember {
        mutableStateListOf(false, false)
    }

    MultiChoiceSegmentedButtonRow(
        modifier = Modifier.fillMaxWidth()
    ) {
        options.forEachIndexed { index, label ->
            SegmentedButton(
                shape = SegmentedButtonDefaults.itemShape(
                    index = index,
                    count = options.size
                ),
                checked = selectedOptions[index],
                onCheckedChange = {
                    selectedOptions[index] = !selectedOptions[index]
                },
                icon = { SegmentedButtonDefaults.Icon(selectedOptions[index]) },
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