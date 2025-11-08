package org.example

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

// === МОДЕЛИ ДАННЫХ ===

data class Nutrient(
    val name: String,
    val amount: Double,
    val unit: String
)

data class FoodItem(
    val id: Int,
    val name: String,
    val nutrients: List<Nutrient>
)

// === СОСТОЯНИЕ ПРИЛОЖЕНИЯ ===

class AppState {
    var currentScreen by mutableStateOf<Screen>(Screen.Main)
    var foodItems = mutableStateListOf<FoodItem>()
    var newFoodName by mutableStateOf("")

    // Данные для нового продукта
    var newProtein by mutableStateOf("")
    var newCarbs by mutableStateOf("")
    var newFat by mutableStateOf("")
    var newFiber by mutableStateOf("")
    var newMagnesium by mutableStateOf("")
    var newZinc by mutableStateOf("")
    var newSelenium by mutableStateOf("")
    var newVitaminC by mutableStateOf("")
    var newVitaminB1 by mutableStateOf("")

    private var nextId = 1

    fun addFoodItem() {
        if (newFoodName.isBlank()) return

        val nutrients = mutableListOf<Nutrient>()

        // Макронутриенты
        if (newProtein.isNotBlank()) nutrients.add(Nutrient("Белки", newProtein.toDoubleOrNull() ?: 0.0, "г"))
        if (newCarbs.isNotBlank()) nutrients.add(Nutrient("Углеводы", newCarbs.toDoubleOrNull() ?: 0.0, "г"))
        if (newFat.isNotBlank()) nutrients.add(Nutrient("Жиры", newFat.toDoubleOrNull() ?: 0.0, "г"))
        if (newFiber.isNotBlank()) nutrients.add(Nutrient("Клетчатка", newFiber.toDoubleOrNull() ?: 0.0, "г"))

        // Микронутриенты
        if (newMagnesium.isNotBlank()) nutrients.add(Nutrient("Магний", newMagnesium.toDoubleOrNull() ?: 0.0, "мг"))
        if (newZinc.isNotBlank()) nutrients.add(Nutrient("Цинк", newZinc.toDoubleOrNull() ?: 0.0, "мг"))
        if (newSelenium.isNotBlank()) nutrients.add(Nutrient("Селен", newSelenium.toDoubleOrNull() ?: 0.0, "мкг"))
        if (newVitaminC.isNotBlank()) nutrients.add(Nutrient("Витамин C", newVitaminC.toDoubleOrNull() ?: 0.0, "мг"))
        if (newVitaminB1.isNotBlank()) nutrients.add(Nutrient("Витамин B1", newVitaminB1.toDoubleOrNull() ?: 0.0, "мг"))

        val newItem = FoodItem(nextId++, newFoodName, nutrients)
        foodItems.add(newItem)

        // Очищаем форму
        clearForm()
        currentScreen = Screen.Main
    }

    private fun clearForm() {
        newFoodName = ""
        newProtein = ""
        newCarbs = ""
        newFat = ""
        newFiber = ""
        newMagnesium = ""
        newZinc = ""
        newSelenium = ""
        newVitaminC = ""
        newVitaminB1 = ""
    }
}

// === ЭКРАНЫ ПРИЛОЖЕНИЯ ===

sealed class Screen {
    object Main : Screen()
    object AddFood : Screen()
}

// === ПОЛЬЗОВАТЕЛЬСКИЙ ИНТЕРФЕЙС ===

@Composable
fun NutritionApp(state: AppState) {
    Scaffold(
        topBar = { TopAppBarSection(state) }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when (state.currentScreen) {
                is Screen.Main -> MainScreen(state)
                is Screen.AddFood -> AddFoodScreen(state)
            }
        }
    }
}

@Composable
fun TopAppBarSection(state: AppState) {
    TopAppBar(
        title = { Text("Трекер питания") },
        actions = {
            IconButton(onClick = {
                state.currentScreen = Screen.AddFood
            }) {
                Icon(Icons.Default.Add, contentDescription = "Добавить продукт")
            }
            IconButton(onClick = { /* Статистика - временно убрана */ }) {
                Icon(Icons.Default.Info, contentDescription = "Информация")
            }
        },
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = Color.White
    )
}

@Composable
fun MainScreen(state: AppState) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
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
        if (state.foodItems.isEmpty()) {
            EmptyState()
        } else {
            FoodItemsList(state)
        }
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
fun NutritionStat(name: String, value: String, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(name, style = MaterialTheme.typography.caption, color = Color.Gray)
        Spacer(modifier = Modifier.height(4.dp))
        Text(value, style = MaterialTheme.typography.h6, color = color)
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
                Icons.Default.Phone, // Заменили Restaurant на Fastfood
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
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        }
    }
}

@Composable
fun FoodItemsList(state: AppState) {
    Column {
        Text(
            "Добавленные продукты:",
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        state.foodItems.forEach { foodItem ->
            FoodItemCard(foodItem, modifier = Modifier.padding(vertical = 6.dp))
        }
    }
}

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

@Composable
fun NutrientRow(nutrient: Nutrient) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(nutrient.name, style = MaterialTheme.typography.body2)
        Text("${nutrient.amount} ${nutrient.unit}", style = MaterialTheme.typography.body2)
    }
}

@Composable
fun AddFoodScreen(state: AppState) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        // Заголовок с кнопкой назад
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 20.dp)
        ) {
            IconButton(onClick = { state.currentScreen = Screen.Main }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Назад")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                "Добавить продукт",
                style = MaterialTheme.typography.h4,
                color = MaterialTheme.colors.primary
            )
        }

        // Форма добавления
        AddFoodForm(state)
    }
}

@Composable
fun AddFoodForm(state: AppState) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = 4.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Название продукта
            OutlinedTextField(
                value = state.newFoodName,
                onValueChange = { state.newFoodName = it },
                label = { Text("Название продукта") },
                placeholder = { Text("Например: Яблоко, Куриная грудка...") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Макронутриенты
            Text("Макронутриенты (г):", style = MaterialTheme.typography.h6)
            Spacer(modifier = Modifier.height(12.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                NutrientInputField(
                    value = state.newProtein,
                    onValueChange = { state.newProtein = it },
                    label = "Белки",
                    modifier = Modifier.weight(1f)
                )
                NutrientInputField(
                    value = state.newCarbs,
                    onValueChange = { state.newCarbs = it },
                    label = "Углеводы",
                    modifier = Modifier.weight(1f)
                )
                NutrientInputField(
                    value = state.newFat,
                    onValueChange = { state.newFat = it },
                    label = "Жиры",
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            NutrientInputField(
                value = state.newFiber,
                onValueChange = { state.newFiber = it },
                label = "Клетчатка (г)",
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Микронутриенты
            Text("Микронутриенты:", style = MaterialTheme.typography.h6)
            Spacer(modifier = Modifier.height(12.dp))

            NutrientInputField(
                value = state.newMagnesium,
                onValueChange = { state.newMagnesium = it },
                label = "Магний (мг)",
                modifier = Modifier.fillMaxWidth()
            )

            NutrientInputField(
                value = state.newZinc,
                onValueChange = { state.newZinc = it },
                label = "Цинк (мг)",
                modifier = Modifier.fillMaxWidth()
            )

            NutrientInputField(
                value = state.newSelenium,
                onValueChange = { state.newSelenium = it },
                label = "Селен (мкг)",
                modifier = Modifier.fillMaxWidth()
            )

            NutrientInputField(
                value = state.newVitaminC,
                onValueChange = { state.newVitaminC = it },
                label = "Витамин C (мг)",
                modifier = Modifier.fillMaxWidth()
            )

            NutrientInputField(
                value = state.newVitaminB1,
                onValueChange = { state.newVitaminB1 = it },
                label = "Витамин B1 (мг)",
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Кнопка добавления
            Button(
                onClick = { state.addFoodItem() },
                modifier = Modifier.fillMaxWidth(),
                enabled = state.newFoodName.isNotBlank(),
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary)
            ) {
                Text("Добавить продукт", color = Color.White)
            }

            // Подсказка
            Text(
                "Примечание: можно заполнять только те поля, которые вас интересуют",
                style = MaterialTheme.typography.caption,
                color = Color.Gray,
                modifier = Modifier.padding(top = 12.dp)
            )
        }
    }
}

@Composable
fun NutrientInputField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = { newValue ->
            // Разрешаем только числа и точку
            if (newValue.isEmpty() || newValue.matches(Regex("^\\d*\\.?\\d*$"))) {
                onValueChange(newValue)
            }
        },
        label = { Text(label) },
        modifier = modifier,
        singleLine = true
    )
}

// === ВСПОМОГАТЕЛЬНЫЕ ФУНКЦИИ ===

data class NutritionTotals(
    val protein: Double,
    val carbs: Double,
    val fat: Double
)

fun calculateTotals(foodItems: List<FoodItem>): NutritionTotals {
    var protein = 0.0
    var carbs = 0.0
    var fat = 0.0

    foodItems.forEach { food ->
        food.nutrients.forEach { nutrient ->
            when (nutrient.name) {
                "Белки" -> protein += nutrient.amount
                "Углеводы" -> carbs += nutrient.amount
                "Жиры" -> fat += nutrient.amount
            }
        }
    }

    return NutritionTotals(
        protein = Math.round(protein * 100.0) / 100.0,
        carbs = Math.round(carbs * 100.0) / 100.0,
        fat = Math.round(fat * 100.0) / 100.0
    )
}

// === ГЛАВНАЯ ФУНКЦИЯ ===

fun main() = application {
    val appState = remember { AppState() }

    Window(
        onCloseRequest = ::exitApplication,
        title = "Трекер питания - Compose Desktop"
    ) {
        MaterialTheme {
            NutritionApp(appState)
        }
    }
}