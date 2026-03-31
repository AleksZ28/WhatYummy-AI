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
import com.azurowski.whatyummyai.main.ui.theme.getBackground
import com.frosch2010.fuzzywuzzy_kotlin.FuzzySearch

@Composable
fun HomeScreen(
    navController: NavController,
    homeViewModel: HomeViewModel = viewModel(),
    kitchenViewModel: KitchenViewModel = viewModel()
){

    val recipes by homeViewModel.recipes.collectAsState()
    val kitchenItems by kitchenViewModel.kitchenItems.collectAsState()
    val textFieldState = remember { TextFieldState() }
    val openDialog = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        homeViewModel.fetchRecipes()
        kitchenViewModel.observeItems()
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

    val context = LocalContext.current
    val prefs = remember {
        context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
    }

    val themeId = prefs.getInt("themeId", 1)

    AddKitchenItemDialog(
        showDialog = openDialog.value,
        onDismiss = { openDialog.value = false },
        onConfirm = { kitchenViewModel.addItem(it); openDialog.value = false }
    )

    Column(
        modifier = Modifier.fillMaxSize().background(brush = getBackground(themeId)),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        SearchBar(
            textFieldState = textFieldState,
            onSearch = { query ->
                println("Szukam: $query")
            },
            searchResults = searchResults,
            placeholderText = "Szukaj przepisu...",
            onRecipeClick = { recipeId, recipeTitle ->
                navController.navigate(RecipeRoute(recipeId, recipeTitle))
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
                RecentRecipesSection()

                Spacer(modifier = Modifier.height(16.dp))

                YourKitchenSection(
                    kitchenItems = kitchenItems,
                    onDelete = { kitchenViewModel.deleteItem(it) },
                    recipes = recipes,
                    onRecipeClick = { recipeId, recipeTitle ->
                        navController.navigate(RecipeRoute(recipeId, recipeTitle))
                    }
                )
            }

            AddToKitchenButton(modifier = Modifier.align(Alignment.BottomEnd), onClick = { openDialog.value = true })

        }

        HomeBottomBar(navigateToAddRecipe = { navController.navigate(AddRecipeRoute) })
    }
}