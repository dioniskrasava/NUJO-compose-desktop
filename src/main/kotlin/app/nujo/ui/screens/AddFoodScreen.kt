package app.nujo.ui.screens

import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

import app.nujo.model.Screen
import app.nujo.state.AppState
import app.nujo.ui.components.NutrientInputField
import app.nujo.ui.screens.AddFoodForm


@Composable
fun AddFoodScreen(state: AppState) {
    // Состояние для вертикальной прокрутки с видимым скроллбаром
    val verticalScrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()

    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(verticalScrollState)
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

            // Добавляем дополнительный контент для демонстрации прокрутки
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                "Дополнительные поля (для демонстрации прокрутки):",
                style = MaterialTheme.typography.h6,
                modifier = Modifier.padding(bottom = 12.dp)
            )
            repeat(5) { index ->
                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    label = { Text("Дополнительное поле ${index + 1}") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    enabled = false
                )
            }

            // Добавляем отступ внизу для удобства прокрутки
            Spacer(modifier = Modifier.height(32.dp))
        }

        // ВИДИМЫЙ СКРОЛЛБАР - добавляем справа
        VerticalScrollbar(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .fillMaxHeight(),

            adapter = rememberScrollbarAdapter(verticalScrollState)
        )
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
