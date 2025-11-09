package app.nujo.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

import app.nujo.model.Screen
import app.nujo.state.AppState

@Composable
fun SettingsScreen(state: AppState) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Заголовок с кнопкой назад
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 32.dp)
        ) {
            IconButton(onClick = { state.currentScreen = Screen.Main }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Назад")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                "Настройки",
                style = MaterialTheme.typography.h4,
                color = MaterialTheme.colors.primary
            )
        }

        // Карточка с настройками темы
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = 4.dp
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    "Внешний вид",
                    style = MaterialTheme.typography.h6,
                    color = MaterialTheme.colors.primary
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Переключатель темы
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Тёмная тема")

                    Switch(
                        checked = state.isDarkTheme,
                        onCheckedChange = { state.isDarkTheme = it },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = MaterialTheme.colors.primary,
                            checkedTrackColor = MaterialTheme.colors.primary.copy(alpha = 0.5f)
                        )
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Описание
                Text(
                    "Переключение между светлой и тёмной темой приложения",
                    style = MaterialTheme.typography.caption,
                    color = Color.Gray
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Дополнительные настройки (можно добавить позже)
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = 4.dp
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    "Дополнительные настройки",
                    style = MaterialTheme.typography.h6,
                    color = MaterialTheme.colors.primary
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Заглушки для будущих настроек
                SettingItem(
                    title = "Уведомления",
                    description = "Настройка уведомлений о питании",
                    enabled = false
                )

                SettingItem(
                    title = "Экспорт данных",
                    description = "Экспорт ваших данных о питании",
                    enabled = false
                )

                SettingItem(
                    title = "О приложении",
                    description = "Версия и информация о разработчике",
                    enabled = false
                )
            }
        }
    }
}

@Composable
private fun SettingItem(title: String, description: String, enabled: Boolean = true) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    title,
                    style = MaterialTheme.typography.body1,
                    color = if (enabled) MaterialTheme.colors.onSurface else Color.Gray
                )
                Text(
                    description,
                    style = MaterialTheme.typography.caption,
                    color = if (enabled) Color.Gray else Color.Gray.copy(alpha = 0.5f)
                )
            }

            IconButton(
                onClick = { /* TODO */ },
                enabled = enabled
            ) {
                Icon(
                    Icons.Default.ArrowForward,
                    contentDescription = "Перейти",
                    tint = if (enabled) MaterialTheme.colors.primary else Color.Gray
                )
            }
        }

        // Разделитель
        if (enabled) {
            Divider(
                modifier = Modifier.padding(top = 12.dp),
                color = Color.Gray.copy(alpha = 0.2f)
            )
        }
    }
}