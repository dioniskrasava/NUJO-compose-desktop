package org.example

import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.example.state.AppState
import org.example.ui.NutritionApp
import org.example.ui.NutritionTrackerTheme


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