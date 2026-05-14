package com.azurowski.whatyummyai.main.ui.screens.home

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.azurowski.whatyummyai.main.ui.components.AddKitchenItemDialog
import com.azurowski.whatyummyai.main.ui.components.SearchBar
import com.azurowski.whatyummyai.main.ui.components.home.AddToKitchenButton
import com.azurowski.whatyummyai.main.ui.components.home.HomeBottomBar
import com.azurowski.whatyummyai.main.ui.components.home.RecentRecipesSection
import com.azurowski.whatyummyai.main.ui.components.home.YourKitchenSection
import com.azurowski.whatyummyai.main.ui.screens.AddRecipeRoute
import com.azurowski.whatyummyai.main.ui.screens.RecipeRoute
import com.azurowski.whatyummyai.main.ui.theme.White50
import com.frosch2010.fuzzywuzzy_kotlin.FuzzySearch

@Composable
fun HomeScreen(
    navController: NavController,
    homeViewModel: HomeViewModel = viewModel(),
    kitchenViewModel: KitchenViewModel = viewModel()
){

    val recipes by homeViewModel.recipes.collectAsState()
    val recentRecipes by homeViewModel.recentRecipes.collectAsState()
    val kitchenItems by kitchenViewModel.kitchenItems.collectAsState()
    val textFieldState = remember { TextFieldState() }
    val openDialog = remember { mutableStateOf(false) }
    val selectedCategory = remember { mutableStateOf("Wszystkie") }
    val onlyGlutenFree = remember { mutableStateOf(false) }

    val context = LocalContext.current
    val prefs = remember {
        context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
    }

    val filteredRecipes = remember(recipes, kitchenItems, selectedCategory.value, onlyGlutenFree.value) {
        if (kitchenItems.isEmpty()) {
            emptyList()
        } else {
            recipes.filter { recipe ->
                val categoryMatch = selectedCategory.value == "Wszystkie" || recipe.categories.any {
                    it.equals(selectedCategory.value, ignoreCase = true)
                }
                val onlyGlutenFreeMatch = onlyGlutenFree.value == recipe.isGlutenFree || !onlyGlutenFree.value
                onlyGlutenFreeMatch && categoryMatch && recipe.ingredientNames.any() { ingredient ->
                    kitchenItems.any { kitchenItem ->
                        FuzzySearch.partialRatio(
                            ingredient.lowercase(),
                            kitchenItem.name.lowercase()
                        ) > 70
                    }
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        homeViewModel.fetchRecipes()
        kitchenViewModel.observeItems()

        val currentRecent = prefs.getString("recentRecipeIds", "") ?: ""
        if (currentRecent.isNotEmpty()) {
            homeViewModel.setRecentRecipeIds(currentRecent.split(","))
        }
    }

    val searchResults = remember(textFieldState.text, recipes) {
        val query = textFieldState.text.toString()
        if (query.isBlank()) {
            recipes
        } else {
            val topResults = FuzzySearch.extractTop(
                query,
                recipes.map { it.title },
                10,
                70
            )

            topResults.mapNotNull { result ->
                recipes.find { it.title == result.string }
            }
        }
    }

    AddKitchenItemDialog(
        showDialog = openDialog.value,
        onDismiss = { openDialog.value = false },
        onConfirm = { kitchenViewModel.addItem(it); openDialog.value = false }
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        SearchBar(
            textFieldState = textFieldState,
            onSearch = { query ->
                println("Szukam: $query")
            },
            searchResults = searchResults,
            placeholderText = "Szukaj przepisu...",
            onRecipeClick = { recipeId, recipeTitle, recipeIsGlutenFree ->
                navController.navigate(RecipeRoute(recipeId, recipeTitle, recipeIsGlutenFree))
            }
        )

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .background(color = White50, shape = RoundedCornerShape(45.dp))
        ) {

            Column(
                modifier = Modifier
                    .padding(vertical = 40.dp, horizontal = 30.dp)
            ) {
                RecentRecipesSection(
                    recipes = recentRecipes,
                    onRecipeClick = { recipeId, recipeTitle, recipeIsGlutenFree ->
                        navController.navigate(RecipeRoute(recipeId, recipeTitle, recipeIsGlutenFree))
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                YourKitchenSection(
                    kitchenItems = kitchenItems,
                    onDelete = { kitchenViewModel.deleteItem(it) },
                    recipes = filteredRecipes,
                    onRecipeClick = { recipeId, recipeTitle, recipeIsGlutenFree ->
                        navController.navigate(RecipeRoute(recipeId, recipeTitle, recipeIsGlutenFree))
                    }
                )
            }

            AddToKitchenButton(modifier = Modifier.align(Alignment.BottomEnd), onClick = { openDialog.value = true })

        }

        HomeBottomBar(
            navigateToAddRecipe = { navController.navigate(AddRecipeRoute) },
            selectedCategory = selectedCategory.value,
            onCategorySelected = { selectedCategory.value = it },
            onlyGlutenFree = onlyGlutenFree.value,
            onlyGlutenFreeChanged = { onlyGlutenFree.value = !onlyGlutenFree.value }
        )
    }
}