package app.nujo.model

sealed class Screen {
    object Main : Screen()
    object AddFood : Screen()
    object Settings : Screen()
}