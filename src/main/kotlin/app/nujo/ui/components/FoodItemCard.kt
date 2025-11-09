package app.nujo.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

import app.nujo.model.FoodItem
import app.nujo.ui.components.NutrientRow

@Composable
fun FoodItemCard(foodItem: FoodItem, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = 2.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Название продукта
            Text(
                foodItem.name,
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.primary
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Нутриенты
            if (foodItem.nutrients.isNotEmpty()) {
                Column {
                    foodItem.nutrients.take(3).forEach { nutrient ->
                        NutrientRow(nutrient)
                    }
                    if (foodItem.nutrients.size > 3) {
                        Text(
                            "+ ещё ${foodItem.nutrients.size - 3} нутриентов",
                            style = MaterialTheme.typography.caption,
                            color = Color.Gray
                        )
                    }
                }
            }
        }
    }
}