package com.azurowski.whatyummyai.main.ui.components.addRecipe

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ButtonGroupDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Text
import androidx.compose.material3.ToggleButton
import androidx.compose.material3.ToggleButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.azurowski.whatyummyai.main.ui.theme.GrayStatic
import com.azurowski.whatyummyai.main.ui.theme.White25

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun CategoriesSelection(){
    val categories = listOf("Śniadanie", "Obiad", "Kolacja", "Deser", "Przekąska", "Napój", "Inne")
    val selectedCategories = remember { mutableStateListOf<Int>() }

    FlowRow(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(2.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
        maxItemsInEachRow = 3
    ) {
        categories.forEachIndexed { index, label ->
            ToggleButton(
                checked = selectedCategories.contains(index),
                onCheckedChange = { if (selectedCategories.contains(index)) selectedCategories.remove(index) else selectedCategories.add(index) },
                modifier = Modifier.semantics { role = Role.RadioButton },
                shapes = ButtonGroupDefaults.connectedMiddleButtonShapes(),
                colors = ToggleButtonDefaults.toggleButtonColors(containerColor = White25, checkedContainerColor = GrayStatic, checkedContentColor = Color.Black)
            ) {
                Text(label)
            }
        }
    }
}