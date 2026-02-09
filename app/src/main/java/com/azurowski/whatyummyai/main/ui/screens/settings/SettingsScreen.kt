package com.azurowski.whatyummyai.main.ui.screens.settings

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.azurowski.whatyummyai.main.ui.theme.FirstThemeColor1
import com.azurowski.whatyummyai.main.ui.theme.FourthThemeColor1
import com.azurowski.whatyummyai.main.ui.theme.SecondThemeColor1
import com.azurowski.whatyummyai.main.ui.theme.ThirdThemeColor1
import com.azurowski.whatyummyai.main.ui.theme.White50
import com.azurowski.whatyummyai.main.ui.theme.White75
import com.azurowski.whatyummyai.main.ui.theme.getBackground
import com.google.firebase.auth.FirebaseAuth

@Composable
fun SettingsScreen(
    navController: NavController
) {
    val context = LocalContext.current
    val prefs = remember {
        context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
    }
    var themeId = remember{prefs.getInt("themeId", 1)}

    val buttonColor = when (themeId) {
        1 -> FirstThemeColor1
        2 -> SecondThemeColor1
        3 -> ThirdThemeColor1
        4 -> FourthThemeColor1
        else -> FirstThemeColor1
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = getBackground(themeId))
            .padding(16.dp)
            .statusBarsPadding(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = White50, shape = RoundedCornerShape(45.dp))
                .weight(1f)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Ustawienia",
                fontSize = 24.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,

            )
            Spacer(modifier = Modifier.weight(0.03f))
            HorizontalDivider(color = Color.Black, thickness = 1.dp)
            Column(
                modifier = Modifier
                    .padding(vertical = 16.dp, horizontal = 16.dp)
                    .background(color = White75, shape = RoundedCornerShape(45.dp))
                    .padding(vertical = 16.dp, horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(text="Zmień motyw",
                    fontSize = 18.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    (1..4).forEach { id ->
                        ThemeSelectorButton(
                            themeId = id,
                            onClick = {
                                prefs.edit().putInt("themeId", id).apply()
                                themeId = id
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    FirebaseAuth.getInstance().signOut()
                    navController.navigate("auth") {
                        popUpTo(navController.graph.id) {
                            inclusive = true
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = buttonColor)
            ) {
                Text("Logout")
            }
        }
    }
}

@Composable
private fun ThemeSelectorButton(themeId: Int, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.size(48.dp),
        shape = CircleShape,
        contentPadding = PaddingValues(0.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(brush = getBackground(themeId = themeId))
        )
    }
}

@Preview
@Composable
fun SettingsScreenPreview() {
    SettingsScreen(rememberNavController())
}
