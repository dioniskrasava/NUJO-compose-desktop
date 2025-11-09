package app.nujo.model

data class FoodItem(
    val id: Int,
    val name: String,
    val nutrients: List<Nutrient>
)