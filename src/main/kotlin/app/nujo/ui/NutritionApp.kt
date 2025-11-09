package app.nujo.ui


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings  // ← Новая иконка
import androidx.compose.ui.graphics.Color

import app.nujo.state.AppState
import app.nujo.ui.screens.MainScreen
import app.nujo.ui.screens.AddFoodScreen
import app.nujo.ui.screens.SettingsScreen  // ← Импортируем новый экран
import app.nujo.model.Screen

@Composable
fun NutritionApp(state: AppState) {
    // Настраиваем скроллбар для всего приложения
    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = { TopAppBarSection(state) }
        ) { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                when (state.currentScreen) {
                    is Screen.Main -> MainScreen(state)
                    is Screen.AddFood -> AddFoodScreen(state)
                    is Screen.Settings -> SettingsScreen(state)  // ← Добавляем экран настроек
                }
            }
        }
    }
}


@Composable
private fun TopAppBarSection(state: AppState) {
    TopAppBar(
        title = { Text("Трекер питания", color = MaterialTheme.colors.primary) },
        actions = {
            IconButton(onClick = {
                state.currentScreen = Screen.AddFood
            }) {
                Icon(Icons.Default.Add, contentDescription = "Добавить продукт", tint = MaterialTheme.colors.primary)
            }
            IconButton(onClick = {
                state.currentScreen = Screen.Settings  // ← Открываем настройки
            }) {
                Icon(Icons.Default.Settings, contentDescription = "Настройки", tint = MaterialTheme.colors.primary)
            }
        },
        backgroundColor = MaterialTheme.colors.primary.copy(alpha = 0.1f),
        contentColor = Color.White
    )
}