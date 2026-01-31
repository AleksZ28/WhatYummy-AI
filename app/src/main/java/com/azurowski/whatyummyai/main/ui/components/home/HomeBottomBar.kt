package com.azurowski.whatyummyai.main.ui.components.home

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.SplitButtonDefaults
import androidx.compose.material3.SplitButtonLayout
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipAnchorPosition
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.unit.dp
import com.azurowski.whatyummyai.main.ui.theme.GrayStatic
import com.azurowski.whatyummyai.main.ui.theme.White50

@OptIn(ExperimentalMaterial3ExpressiveApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HomeBottomBar(){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .padding(start = 24.dp, end = 24.dp, bottom = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = White50, shape = RoundedCornerShape(999.dp))
                .padding(horizontal = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            var checked by remember { mutableStateOf(false) }
            Box() {
                SplitButtonLayout(
                    leadingButton = {
                        SplitButtonDefaults.LeadingButton(
                            onClick = { /* Do Nothing */ },
                            colors = ButtonDefaults.buttonColors(containerColor = White50, contentColor = Color.Black)
                        ) {
                            Icon(
                                Icons.Filled.Edit,
                                modifier = Modifier.size(SplitButtonDefaults.LeadingIconSize),
                                contentDescription = "Localized description",
                            )
                            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                            Text("Śniadanie")
                        }
                    },
                    trailingButton = {
                        val description = "Toggle Button"
                        // Icon-only trailing button should have a tooltip for a11y.
                        TooltipBox(
                            positionProvider =
                                TooltipDefaults.rememberTooltipPositionProvider(
                                    TooltipAnchorPosition.Above
                                ),
                            tooltip = { PlainTooltip { Text(description) } },
                            state = rememberTooltipState(),
                        ) {
                            SplitButtonDefaults.TrailingButton(
                                checked = checked,
                                onCheckedChange = { checked = it },
                                modifier =
                                    Modifier.semantics {
                                        stateDescription = if (checked) "Expanded" else "Collapsed"
                                        contentDescription = description
                                    },
                                colors = ButtonDefaults.buttonColors(containerColor = White50, contentColor = Color.Black)
                            ) {
                                val rotation: Float by
                                animateFloatAsState(
                                    targetValue = if (checked) 180f else 0f,
                                    label = "Trailing Icon Rotation",
                                )
                                Icon(
                                    Icons.Filled.KeyboardArrowDown,
                                    modifier =
                                        Modifier.size(SplitButtonDefaults.TrailingIconSize).graphicsLayer {
                                            this.rotationZ = rotation
                                        },
                                    contentDescription = "Localized description",
                                )
                            }
                        }
                    },
                )
            }

            DropdownMenu(
                expanded = checked,
                onDismissRequest = { checked = false },
                shape = RoundedCornerShape(16.dp),
                containerColor = GrayStatic
            ) {
                DropdownMenuItem(
                    text = { Text("Śniadanie") },
                    onClick = { /* Handle edit! */ },
                )
                DropdownMenuItem(
                    text = { Text("Obiad") },
                    onClick = { /* Handle settings! */ },
                )
                DropdownMenuItem(
                    text = { Text("Kolacja") },
                    onClick = { /* Handle send feedback! */ },
                )
            }



            Row() {
                IconButton(onClick = { /* do something */ }) {
                    Icon(Icons.Filled.Add, contentDescription = "Dodaj przepis")
                }
                IconButton(onClick = { /* do something */ }) {
                    Icon(
                        Icons.Filled.Menu,
                        contentDescription = "Otwórz menu",
                    )
                }
            }
        }
    }
}