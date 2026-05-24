package com.azurowski.whatyummyai.main.ui.screens.settings

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.azurowski.whatyummyai.intro.IntroActivity
import com.azurowski.whatyummyai.main.ui.components.OneButtonBottomBar
import com.azurowski.whatyummyai.main.ui.theme.White50
import com.azurowski.whatyummyai.main.ui.theme.White75
import com.azurowski.whatyummyai.main.ui.theme.getBackground
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

@Composable
fun SettingsScreen(navController: NavController, onThemeChange: (Int) -> Unit) {
    val context = LocalContext.current
    val activity = context as? Activity
    val prefs = remember { context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE) }
    var selectedTheme by remember { mutableIntStateOf(prefs.getInt("themeId", 1)) }

    val auth = Firebase.auth
    val username = auth.currentUser?.displayName?.substringBefore(" ")

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .background(
                    color = White50,
                    shape = RoundedCornerShape(
                        topStart = 0.dp,
                        topEnd = 0.dp,
                        bottomStart = 45.dp,
                        bottomEnd = 45.dp
                    )
                )
        ) {

            Column(
                modifier = Modifier.statusBarsPadding()
            ) {
                Row(
                    modifier = Modifier.padding(vertical = 12.dp, horizontal = 24.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = { navController.navigateUp() },
                        modifier = Modifier.background(White50, RoundedCornerShape(100))
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Powrót",
                        )
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 30.dp, end = 30.dp, bottom = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "Cześć $username!",
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 36.sp
                        )
                    )

                    Text(
                        text = "Motyw aplikacji:",
                        style = TextStyle(
                            fontWeight = FontWeight.Medium,
                            fontSize = 16.sp
                        )
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        for (i in 1..3) {
                            ThemeOption(id = i, isSelected = selectedTheme == i) {
                                onThemeChange(i)
                                selectedTheme = i
                            }
                        }
                    }
                }
            }
        }

        OneButtonBottomBar(
            text = "Wyloguj się",
            onClick = {
                FirebaseAuth.getInstance().signOut()
                val intent = Intent(context, IntroActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                context.startActivity(intent)
                activity?.finish()
            }
        )
    }
}

@Composable
fun ThemeOption(id: Int, isSelected: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(64.dp)
            .clip(RoundedCornerShape(100))
            .background(getBackground(id))
            .then(
                if (isSelected) {
                    Modifier.border(
                        width = 4.dp,
                        color = White75,
                        shape = RoundedCornerShape(100)
                    )
                } else {
                    Modifier
                }
            )
            .clickable(onClick = onClick)
    )
}
