package com.azurowski.whatyummyai.main.ui.screens.cooking

import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.azurowski.whatyummyai.main.ui.components.NoBackTopBar
import com.azurowski.whatyummyai.main.ui.components.OneButtonBottomBar
import com.azurowski.whatyummyai.main.ui.components.cooking.RemainingStepsText
import com.azurowski.whatyummyai.main.ui.components.cooking.StepItem
import com.azurowski.whatyummyai.main.ui.components.cooking.StepsIndicatorRow
import com.azurowski.whatyummyai.main.ui.screens.recipe.RecipeViewModel
import com.azurowski.whatyummyai.main.ui.theme.White50
import com.azurowski.whatyummyai.main.util.SttHelper
import kotlinx.coroutines.delay

@Composable
fun CookingScreen(
    navController: NavController,
    recipeViewModel: RecipeViewModel,
    cookingViewModel: CookingViewModel,
    recipeId: String,
    recipeTitle: String,
    recipeIsGlutenFree: Boolean
){
    val recipe by recipeViewModel.recipe.collectAsState()
    val pagerState = rememberPagerState(pageCount = {
        recipe.instructions.size
    })
    val listState = rememberLazyListState()

    val context = LocalContext.current
    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            cookingViewModel.startListening()
        }
    }

    LaunchedEffect(Unit) {
        if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED) {
            cookingViewModel.startListening()
        } else {
            permissionLauncher.launch(android.Manifest.permission.RECORD_AUDIO)
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            cookingViewModel.stopListening()
        }
    }

    LaunchedEffect(Unit) {
        cookingViewModel.voiceCommand.collect { command ->
            when (command) {
                SttHelper.VoiceCommand.NEXT -> {
                    if (pagerState.currentPage < pagerState.pageCount - 1) {
                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    }
                }
                SttHelper.VoiceCommand.PREVIOUS -> {
                    if (pagerState.currentPage > 0) {
                        pagerState.animateScrollToPage(pagerState.currentPage - 1)
                    }
                }
                SttHelper.VoiceCommand.REPEAT -> {
                    if (recipe.instructions.isNotEmpty()) {
                        cookingViewModel.repeatStep(recipe.instructions[pagerState.currentPage])
                    }
                }
                else -> {}
            }
        }
    }

    LaunchedEffect(Unit, pagerState.currentPage, recipe.instructions) {
        if (recipe.instructions.isNotEmpty()) {
            delay(400)
            cookingViewModel.speakStep(pagerState.currentPage, recipe.instructions[pagerState.currentPage])
        }
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
                NoBackTopBar(recipeTitle)

                if (recipe.instructions.isNotEmpty()){
                    Column(
                        modifier = Modifier.fillMaxSize()
                    ){
                        HorizontalPager(
                            state = pagerState,
                            modifier = Modifier.weight(1f)
                        ) { page ->
                            StepItem(stepIndex = page, instruction = recipe.instructions[page])
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        RemainingStepsText(pagerState.pageCount - pagerState.currentPage - 1)

                        StepsIndicatorRow(listState = listState, pagerState = pagerState)
                    }
                }
            }
        }

        OneButtonBottomBar(text = "Wyjdź z trybu gotowania", onClick = { navController.navigateUp() })
    }
}