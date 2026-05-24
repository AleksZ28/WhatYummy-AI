package com.azurowski.whatyummyai.main.ui.screens.recipe

import android.content.Context
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.edit
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.azurowski.whatyummyai.main.ui.components.FadingBoxVertical
import com.azurowski.whatyummyai.main.ui.components.recipe.DeleteRecipeDialog
import com.azurowski.whatyummyai.main.ui.components.recipe.IngredientListItem
import com.azurowski.whatyummyai.main.ui.components.recipe.QuickInfoPanel
import com.azurowski.whatyummyai.main.ui.components.recipe.RecipeBottomBar
import com.azurowski.whatyummyai.main.ui.components.recipe.RecipeCarousel
import com.azurowski.whatyummyai.main.ui.components.recipe.RecipeHeader
import com.azurowski.whatyummyai.main.ui.screens.CookingRoute
import com.azurowski.whatyummyai.main.ui.screens.EditRecipeRoute
import com.azurowski.whatyummyai.main.ui.screens.home.KitchenViewModel
import com.azurowski.whatyummyai.main.ui.theme.LightGreen50
import com.azurowski.whatyummyai.main.ui.theme.White50
import com.frosch2010.fuzzywuzzy_kotlin.FuzzySearch
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@Composable
fun RecipeScreen(
    navController: NavController,
    recipeViewModel: RecipeViewModel = viewModel(),
    kitchenViewModel: KitchenViewModel = viewModel(),
    recipeId: String, recipeTitle: String, recipeIsGlutenFree: Boolean,
){
    val recipe by recipeViewModel.recipe.collectAsState()
    val kitchenItems by kitchenViewModel.kitchenItems.collectAsState()
    val context = LocalContext.current

    val auth = Firebase.auth
    val userId = auth.currentUser?.uid.toString()

    val portionCount = remember { mutableIntStateOf(1) }
    var showDeleteDialog by remember { mutableStateOf(false) }

    val annotatedIngredients by remember(recipe, kitchenItems, portionCount) {
        derivedStateOf {
            recipe.ingredients.map { ingredient ->
                val ingredientName = ingredient.ingredient.lowercase()
                val inKitchen = kitchenItems.any { kitchenItem ->
                    FuzzySearch.partialRatio(
                        ingredientName,
                        kitchenItem.name.lowercase().trim()
                    ) > 70
                }
                val updatedIngredient = ingredient.copy(amount = ingredient.amount * portionCount.intValue)

                updatedIngredient to inKitchen
            }.sortedByDescending {it.second}
        }
    }

    DeleteRecipeDialog(
        showDialog = showDeleteDialog,
        onDismiss = { showDeleteDialog = false },
        onConfirm = {
            showDeleteDialog = false
            recipeViewModel.deleteRecipe(recipeId) {
                navController.navigateUp()
            }
        }
    )

    LaunchedEffect(Unit) {
        recipeViewModel.fetchRecipe(recipeId)
        kitchenViewModel.observeItems()
        Log.d("Recipe", "Recipe: $recipe")

        val prefs = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val currentRecent = prefs.getString("recentRecipeIds", "") ?: ""
        val idsList = if (currentRecent.isEmpty()) mutableListOf() else currentRecent.split(",").toMutableList()

        idsList.remove(recipeId)
        idsList.add(0, recipeId)

        val updatedList = idsList.take(2)
        val updatedString = updatedList.joinToString(",")
        
        prefs.edit { putString("recentRecipeIds", updatedString) }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        RecipeHeader(navBack = { navController.navigateUp() }, recipeTitle, recipeIsGlutenFree)

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
                    if (recipe.imageUrls.isNotEmpty()) {
                        item {
                            RecipeCarousel(recipe.imageUrls)
                            Spacer(modifier = Modifier.height(12.dp))
                        }
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

                    item {
                        Spacer(modifier = Modifier.height(12.dp))
                    }

                    item {
                        Text(
                            text = "Przygotowanie",
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

                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(20.dp))
                                .background(White50)
                                .padding(24.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            recipe.instructions.forEachIndexed { index, instruction ->
                                Text(
                                    text = (index+1).toString() + ". " + instruction,
                                    style = TextStyle(
                                        fontWeight = FontWeight.Medium,
                                        fontSize = 16.sp
                                    )
                                )
                            }
                        }
                    }

                    item{
                        Spacer(modifier = Modifier.height(64.dp))
                    }
                }
            }

            Button(
                onClick = { navController.navigate(CookingRoute(recipeId, recipeTitle, recipeIsGlutenFree)) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                ),
                modifier = Modifier
                    .padding(bottom = 24.dp)
                    .height(48.dp)
                    .width(192.dp)
                    .align(Alignment.BottomCenter)
                    .dropShadow(
                        shape = RoundedCornerShape(50),
                        block = {
                            radius = 50f
                            spread = 0.25f
                            alpha = 0.2f
                        }
                    )
            ) {
                Text(
                    text = "GOTUJ!",
                    style = TextStyle(
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 16.sp
                    )
                )
            }
        }

        if (recipe.id.isNotEmpty()){
            RecipeBottomBar(
                usersRecipe = userId == recipe.authorId,
                recipeAuthor = recipe.authorName.substringBefore(" "),
                onEditClick = {
                    navController.navigate(EditRecipeRoute(recipeId))
                },
                onDeleteClick = {
                    showDeleteDialog = true
                }
            )
        }
    }
}