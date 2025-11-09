package app.nujo.state

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import app.nujo.model.FoodItem
import app.nujo.model.Nutrient
import app.nujo.model.Screen

class AppState {


    var currentScreen by mutableStateOf<Screen>(Screen.Main)
    var foodItems = mutableStateListOf<FoodItem>()
    var newFoodName by mutableStateOf("")

    var isDarkTheme by mutableStateOf(true)  // ← по умолчанию тёмная

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