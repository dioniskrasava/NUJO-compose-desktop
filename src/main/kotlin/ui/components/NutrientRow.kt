package org.example.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.example.model.Nutrient

@Composable
fun NutrientRow(nutrient: Nutrient) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(nutrient.name, style = MaterialTheme.typography.body2)
        Text("${nutrient.amount} ${nutrient.unit}", style = MaterialTheme.typography.body2)
    }
}