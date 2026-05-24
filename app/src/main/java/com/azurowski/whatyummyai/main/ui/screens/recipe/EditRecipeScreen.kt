package com.azurowski.whatyummyai.main.ui.screens.recipe

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.azurowski.whatyummyai.main.ui.components.FormTopBar
import com.azurowski.whatyummyai.main.ui.components.OneButtonBottomBar
import com.azurowski.whatyummyai.main.ui.components.addRecipe.AddRecipeForm
import com.azurowski.whatyummyai.main.ui.screens.HomeRoute
import com.azurowski.whatyummyai.main.ui.theme.White50

@Composable
fun EditRecipeScreen(
    navController: NavController,
    editRecipeViewModel: EditRecipeViewModel = viewModel(),
    recipeId: String
){

    val state by editRecipeViewModel.state.collectAsState()

    LaunchedEffect(recipeId) {
        editRecipeViewModel.fetchRecipe(recipeId)
    }

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
                FormTopBar(title = "Edytuj przepis", navigateBack = { navController.navigateUp() })

                AddRecipeForm(
                    title = state.title,
                    totalTime = state.totalTime,
                    timeUnit = state.timeUnit,
                    images = emptyList(),
                    ingredientsData = state.ingredients,
                    instructions = state.instructions,
                    selectedCategories = state.categories,
                    isPublic = state.public,
                    isGlutenFree = state.glutenFree,
                    showImagePicker = false,
                    onAddIngredient = { editRecipeViewModel.addIngredient() },
                    onUnitSelected = { index, unit -> editRecipeViewModel.updateIngredientUnit(index, unit) },
                    onTimeUnitSelected = { editRecipeViewModel.updateTimeUnit(it) },
                    onAddInstruction = { editRecipeViewModel.addInstruction() },
                    onCategoryToggle = { editRecipeViewModel.toggleCategory(it) },
                    onPublicToggle = { editRecipeViewModel.togglePublic() },
                    onGlutenFreeToggle = { editRecipeViewModel.toggleGlutenFree() }
                )
            }
        }

        OneButtonBottomBar(
            text = if (state.isSaving) "Zapisywanie..." else "Zapisz zmiany",
            onClick = {
                editRecipeViewModel.updateRecipe(recipeId) {
                    navController.navigate(HomeRoute)
                }
            }
        )
    }
}