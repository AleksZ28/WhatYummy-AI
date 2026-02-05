package com.azurowski.whatyummyai.main.ui.screens.recipe

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MultiChoiceSegmentedButtonRow
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.azurowski.whatyummyai.main.ui.components.FormTopBar
import com.azurowski.whatyummyai.main.ui.components.addRecipe.AddRecipeTextField
import com.azurowski.whatyummyai.main.ui.theme.White50

@Composable
fun AddRecipeScreen(navController: NavController){
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .background(color = White50, shape = RoundedCornerShape(topStart = 0.dp, topEnd = 0.dp, bottomStart = 45.dp, bottomEnd = 45.dp))
        ) {

            Column(
                modifier = Modifier.statusBarsPadding()
            ) {
                FormTopBar(title = "Nowy przepis", navigateBack = { navController.popBackStack() })

                Column(
                    modifier = Modifier
                        .padding(vertical = 12.dp, horizontal = 30.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ){
                    AddRecipeTextField(
                        state = rememberTextFieldState(),
                        placeholder = "Nazwa przepisu",
                        lineLimits = TextFieldLineLimits.SingleLine
                    )

                    AddRecipeTextField(
                        state = rememberTextFieldState(),
                        placeholder = "Składnik",
                        lineLimits = TextFieldLineLimits.SingleLine
                    )

                    AddRecipeTextField(
                        state = rememberTextFieldState(),
                        placeholder = "Instrukcje",
                        lineLimits = TextFieldLineLimits.MultiLine(minHeightInLines = 5),
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Default,
                            capitalization = KeyboardCapitalization.Sentences
                        )
                    )


                    val selectedOptions = remember {
                        mutableStateListOf(false, false)
                    }
                    val options = listOf("Private", "GlutenFree")

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
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(start = 24.dp, end = 24.dp, bottom = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(containerColor = White50, contentColor = Color.Black),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 40.dp)
                    .height(48.dp),
            ) {
                Text("Dodaj przepis")
            }
        }
    }
}

@Preview
@Composable
fun AddRecipeScreenPreview(){
    AddRecipeScreen(navController = NavController(context = LocalContext.current))
}