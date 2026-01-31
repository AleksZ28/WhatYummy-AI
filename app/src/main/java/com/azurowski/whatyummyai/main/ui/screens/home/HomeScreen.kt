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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.azurowski.whatyummyai.main.ui.components.SearchBar
import com.azurowski.whatyummyai.main.ui.components.home.AddToKitchenButton
import com.azurowski.whatyummyai.main.ui.components.home.HomeBottomBar
import com.azurowski.whatyummyai.main.ui.components.home.RecentRecipesSection
import com.azurowski.whatyummyai.main.ui.components.home.YourKitchenSection
import com.azurowski.whatyummyai.main.ui.theme.BackgroundMapper
import com.azurowski.whatyummyai.main.ui.theme.White50

@Composable
fun HomeScreen(navController: NavController, viewModel: HomeViewModel = viewModel()){
    val textFieldState = remember { TextFieldState() }

    val searchList = listOf(
        "Test"
    )

    val searchResults = searchList.filter {
        it.contains(textFieldState.text.toString(), ignoreCase = true)
    }

    val recipes by viewModel.recipes.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchRecipes()
    }

    val context = LocalContext.current
    val prefs = remember {
        context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
    }

    val themeId = prefs.getInt("themeId", 1)


    Column(
        modifier = Modifier.fillMaxSize().background(brush = BackgroundMapper(themeId)),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        SearchBar(
            textFieldState = textFieldState,
            onSearch = { query ->
                println("Szukam: $query")
            },
            searchResults = searchResults,
            placeholderText = "Szukaj przepisu..."
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

                YourKitchenSection(recipes)
            }

            AddToKitchenButton(modifier = Modifier.align(Alignment.BottomEnd))

        }

        HomeBottomBar()
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(
        navController = rememberNavController()
    )
}