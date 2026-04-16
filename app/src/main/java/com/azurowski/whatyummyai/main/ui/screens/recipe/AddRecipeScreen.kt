package com.azurowski.whatyummyai.main.ui.screens.recipe

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.azurowski.whatyummyai.main.ui.components.FormTopBar
import com.azurowski.whatyummyai.main.ui.components.OneButtonBottomBar
import com.azurowski.whatyummyai.main.ui.components.addRecipe.AddRecipeForm
import com.azurowski.whatyummyai.main.ui.theme.White50

@OptIn(ExperimentalMaterial3ExpressiveApi::class, ExperimentalLayoutApi::class)
@Composable
fun AddRecipeScreen(
    navController: NavController,
    addRecipeViewModel: AddRecipeViewModel = viewModel()
){

    val state by addRecipeViewModel.state.collectAsState()

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
                FormTopBar(title = "Nowy przepis", navigateBack = { navController.navigateUp() })

                AddRecipeForm(
                    title = state.title,
                    totalMinutes = state.totalMinutes,
                    timeUnit = state.timeUnit,
                    ingredientsData = state.ingredients,
                    instructions = state.instructions,
                    selectedCategories = state.categories,
                    isPublic = state.public,
                    isGlutenFree = state.glutenFree,
                    onAddIngredient = { addRecipeViewModel.addIngredient() },
                    onUnitSelected = { index, unit -> addRecipeViewModel.updateIngredientUnit(index, unit) },
                    onTimeUnitSelected = { addRecipeViewModel.updateTimeUnit(it) },
                    onAddInstruction = { addRecipeViewModel.addInstruction() },
                    onCategoryToggle = { addRecipeViewModel.toggleCategory(it) },
                    onPublicToggle = { addRecipeViewModel.togglePublic() },
                    onGlutenFreeToggle = { addRecipeViewModel.toggleGlutenFree() }
                )
            }
        }

        OneButtonBottomBar(text = "Dodaj przepis", onClick = {
            
        })
    }
}

//@Preview
//@Composable
//fun AddRecipeScreenPreview(){
//    AddRecipeScreen(navController = NavController(context = LocalContext.current))
//}