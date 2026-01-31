package com.azurowski.whatyummyai.main.ui.screens

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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.azurowski.whatyummyai.main.ui.components.home.HomeBottomBar
import com.azurowski.whatyummyai.main.ui.components.home.AddToKitchenButton
import com.azurowski.whatyummyai.main.ui.components.home.RecentRecipesSection
import com.azurowski.whatyummyai.main.ui.components.SearchBar
import com.azurowski.whatyummyai.main.ui.components.home.YourKitchenSection
import com.azurowski.whatyummyai.main.ui.theme.Backgrounds
import com.azurowski.whatyummyai.main.ui.theme.White50

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun HomeScreen(navController: NavController){
    val textFieldState = remember { TextFieldState() }

    val searchList = listOf(
        "Test"
    )

    val searchResults = searchList.filter {
        it.contains(textFieldState.text.toString(), ignoreCase = true)
    }

    Column(
        modifier = Modifier.fillMaxSize().background(brush = Backgrounds.bg1),
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

                YourKitchenSection()
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
        navController = androidx.navigation.compose.rememberNavController()
    )
}