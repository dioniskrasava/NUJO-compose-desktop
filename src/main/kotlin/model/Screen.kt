package org.example.model

sealed class Screen {
    object Main : Screen()
    object AddFood : Screen()
}