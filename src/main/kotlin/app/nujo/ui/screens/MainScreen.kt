package app.nujo.ui.screens

import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

import app.nujo.model.FoodItem
import app.nujo.model.Nutrient
import app.nujo.state.AppState
import app.nujo.ui.components.FoodItemCard
import app.nujo.ui.components.NutritionStat
import app.nujo.utils.calculateTotals


@Composable
fun MainScreen(state: AppState) {
    // Состояние для вертикальной прокрутки с видимым скроллбаром
    val verticalScrollState = rememberScrollState()

    // Создаем много контента для демонстрации прокрутки
    val demoFoodItems = remember {
        List(20) { i ->
            FoodItem(
                id = i + 1,
                name = "Тестовый продукт ${i + 1}",
                nutrients = listOf(
                    Nutrient("Белки", 10.0 + i, "г"),
                    Nutrient("Углеводы", 20.0 + i, "г"),
                    Nutrient("Жиры", 5.0 + i, "г")
                )
            )
        }
    }

    val displayItems = if (state.foodItems.isEmpty()) demoFoodItems else state.foodItems

    Box(
        modifier = Modifier
            .fillMaxSize()

    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(verticalScrollState)
                .padding(16.dp)
        ) {
            // Заголовок
            Text(
                "Мои продукты",
                style = MaterialTheme.typography.h4,
                color = MaterialTheme.colors.primary,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Статистика
            NutritionSummary(state)

            Spacer(modifier = Modifier.height(24.dp))

            // Список продуктов
            if (displayItems.isEmpty()) {
                EmptyState()
            } else {
                FoodItemsList(displayItems)
            }

            // Добавляем много контента для демонстрации прокрутки
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                "Дополнительная информация:",
                style = MaterialTheme.typography.h6,
                modifier = Modifier.padding(bottom = 12.dp)
            )
            repeat(10) { index ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    elevation = 1.dp,
                    backgroundColor = Color.LightGray.copy(alpha = 0.2f)
                ) {
                    Text(
                        "Дополнительная карточка ${index + 1} для демонстрации прокрутки",
                        modifier = Modifier.padding(8.dp),
                        style = MaterialTheme.typography.body2
                    )
                }
            }

            // Добавляем отступ внизу для удобства прокрутки
            Spacer(modifier = Modifier.height(32.dp))
        }

        // ВИДИМЫЙ СКРОЛЛБАР - добавляем справа
        VerticalScrollbar(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .fillMaxHeight(),
            adapter = rememberScrollbarAdapter(verticalScrollState)
        )
    }
}

@Composable
fun NutritionSummary(state: AppState) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = 4.dp,
        backgroundColor = MaterialTheme.colors.primary.copy(alpha = 0.1f)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                "Общая статистика",
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.primary
            )

            Spacer(modifier = Modifier.height(12.dp))

            val totals = calculateTotals(state.foodItems)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                NutritionStat("Белки", "${totals.protein}г", Color(0xFF4CAF50))
                NutritionStat("Углеводы", "${totals.carbs}г", Color(0xFF2196F3))
                NutritionStat("Жиры", "${totals.fat}г", Color(0xFFF44336))
            }
        }
    }
}


@Composable
fun EmptyState() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 32.dp),
        elevation = 2.dp,
        backgroundColor = MaterialTheme.colors.surface
    ) {
        Column(
            modifier = Modifier.padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                Icons.Default.List,
                contentDescription = "Нет продуктов",
                tint = Color.Gray,
                modifier = Modifier.size(48.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                "Пока нет добавленных продуктов",
                style = MaterialTheme.typography.h6,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "Нажмите + в верхнем баре чтобы добавить первый продукт",
                style = MaterialTheme.typography.body2,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )
        }
    }
}


@Composable
fun FoodItemsList(foodItems: List<FoodItem>) {
    Column {
        Text(
            "Добавленные продукты:",
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        foodItems.forEach { foodItem ->
            FoodItemCard(foodItem, modifier = Modifier.padding(vertical = 6.dp))
        }
    }
}