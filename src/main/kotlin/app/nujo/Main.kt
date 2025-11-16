package app.nujo

import androidx.compose.runtime.*
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

import app.nujo.state.AppState
import app.nujo.ui.NutritionApp
import app.nujo.ui.NutritionTrackerTheme

// коммит из 16 ноября 2025 года!

fun main() = application {
    val appState = remember { AppState() }

    Window(
        onCloseRequest = ::exitApplication,
        title = "Трекер питания - Compose Desktop"
    ) {
        NutritionTrackerTheme(
            darkTheme = appState.isDarkTheme  // ← используем настройку из состояния
        ) {
            NutritionApp(appState)
        }
    }
}