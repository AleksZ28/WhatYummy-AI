package com.azurowski.whatyummyai.main.ui.components.recipe

import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.azurowski.whatyummyai.main.model.Ingredient
import com.azurowski.whatyummyai.main.ui.theme.LightGreen50
import com.azurowski.whatyummyai.main.ui.theme.White50

@Composable
fun IngredientListItem(ingredient: Ingredient, inKitchen: Boolean){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
//            .clickable(
//                onClick = {  },
//                interactionSource = remember { MutableInteractionSource() },
//                indication = ripple()
//            ) TODO
            .background(color = if (inKitchen) LightGreen50 else White50)
            .padding(horizontal = 16.dp)
            .height(48.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = ingredient.ingredient,
            style = TextStyle(
                fontWeight = FontWeight.Medium
            ),
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp)
                .basicMarquee(
                    iterations = Int.MAX_VALUE,
                    velocity = 20.dp,
                    repeatDelayMillis = 3000
                )
        )
        Text(
            text = "%.2f".format(ingredient.amount) + " " + ingredient.unit,
            style = TextStyle(
                fontWeight = FontWeight.Medium
            ),
            maxLines = 1
        )
    }
}

@Composable
@Preview
fun Preview(){
    IngredientListItem(Ingredient(100.0, "Mleko", "ml"), true)
}