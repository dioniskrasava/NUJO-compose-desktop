package org.example.utils

import org.example.model.FoodItem

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