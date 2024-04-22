package com.example.onlinestore.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import kotlin.random.Random

@Composable
fun SampleScreen() {
    val color = remember {
        Color(
            red = Random.nextFloat(),
            green = Random.nextFloat(),
            blue = Random.nextFloat(),
            alpha = 1f
        )
    }

    val text = remember {
        listOf("Привет, мир!", "Здравствуйте!", "Добро пожаловать!", "Hello, World!", "Bonjour!").random()
    }

    Box(
        modifier = Modifier.fillMaxSize().background(color),
        contentAlignment = Alignment.Center
    ) {
        androidx.compose.material.Text(
            text = text
        )
    }
}