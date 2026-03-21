package com.azurowski.whatyummyai.main.ui.screens.recipe

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.azurowski.whatyummyai.main.ui.components.FadingBoxVertical
import com.azurowski.whatyummyai.main.ui.components.recipe.IngredientListItem
import com.azurowski.whatyummyai.main.ui.components.recipe.QuickInfoPanel
import com.azurowski.whatyummyai.main.ui.components.recipe.RecipeBottomBar
import com.azurowski.whatyummyai.main.ui.components.recipe.RecipeCarousel
import com.azurowski.whatyummyai.main.ui.components.recipe.RecipeHeader
import com.azurowski.whatyummyai.main.ui.screens.home.KitchenViewModel
import com.azurowski.whatyummyai.main.ui.theme.White50

@Composable
fun RecipeScreen(
    navController: NavController,
    recipeViewModel: RecipeViewModel = viewModel(),
    kitchenViewModel: KitchenViewModel = viewModel(),
    recipeId: String, recipeTitle: String
){
    val recipe by recipeViewModel.recipe.collectAsState()
    val kitchenItems by kitchenViewModel.kitchenItems.collectAsState()

    val portionCount = remember { mutableIntStateOf(1) }

    val annotatedIngredients by remember(recipe, kitchenItems, portionCount) {
        derivedStateOf {
            val kitchenNamesSet = kitchenItems.map { it.name.lowercase().trim() }.toSet()
            recipe.ingredients.map { ingredient ->
                val ingredientName = ingredient.ingredient.lowercase()
                val inKitchen = kitchenNamesSet.any { kitchenItem ->
                    ingredientName.contains(kitchenItem) || kitchenItem.contains(ingredientName)
                }
                val updatedIngredient = ingredient.copy(amount = ingredient.amount * portionCount.intValue)

                updatedIngredient to inKitchen
            }.sortedByDescending {it.second}
        }
    }

    LaunchedEffect(Unit) {
        recipeViewModel.fetchRecipe(recipeId)
        kitchenViewModel.observeItems()
        Log.d("Recipe", "Recipe: $recipe")
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        RecipeHeader(navBack = { navController.popBackStack() }, recipeTitle)

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .background(color = White50, shape = RoundedCornerShape(45.dp))
        ) {
            FadingBoxVertical(Modifier) {

                LazyColumn(
                    contentPadding = PaddingValues(horizontal = 30.dp, vertical = 30.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    item {
                        RecipeCarousel(recipe.imageUrls)
                        Spacer(modifier = Modifier.height(12.dp))
                    }

                    if (recipe.id.isNotEmpty()) {
                        item {
                            QuickInfoPanel(recipe.categories, recipe.totalMinutes, portionCount)
                            Spacer(modifier = Modifier.height(24.dp))
                        }
                    }

                    item {
                        Text(
                            text = "Składniki",
                            style = TextStyle(
                                fontWeight = FontWeight.Bold,
                                fontSize = 24.sp,
                                textAlign = TextAlign.Center
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                    }

                    items(annotatedIngredients) { (ingredient, inKitchen) ->
                        IngredientListItem(ingredient, inKitchen)
                    }
                }
            }
        }

        RecipeBottomBar()
    }
}

@Preview
@Composable
fun RecipeScreenPreview() {
    val navController = rememberNavController()

    RecipeScreen(
        navController = navController,
        recipeId = "123",
        recipeTitle = "Spaghetti Carbonara"
    )
}