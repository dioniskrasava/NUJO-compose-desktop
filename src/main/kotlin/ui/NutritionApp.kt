package org.example.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.example.state.AppState
import org.example.ui.screens.MainScreen
import org.example.ui.screens.AddFoodScreen
import org.example.model.Screen
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Info
import androidx.compose.ui.graphics.Color

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
                }
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