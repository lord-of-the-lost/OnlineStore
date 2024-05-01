package com.example.onlinestore.views.edit_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview(showBackground = true)
@Composable
fun Add() {
    var title by remember {
        mutableStateOf("")
    }
    var price by remember {
        mutableStateOf("")
    }
    var description by remember {
        mutableStateOf("")
    }
    var images by remember {
        mutableStateOf("")
    }
    Column(
        Modifier
            .fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(32.dp))

        EditElement(text = "Title", value = title, onValueChanged = { title = it }, 17, onDeleteClick = {title = ""})

        Spacer(modifier = Modifier.height(32.dp))

        EditElement(text = "Price", value = price, onValueChanged = { price = it }, 17,  onDeleteClick = {price = ""})

        Spacer(modifier = Modifier.height(8.dp))

        EditElementDropDown("1", "2", "3", "4", )

        Spacer(modifier = Modifier.height(8.dp))

        EditElement(
            text = "Description",
            value = description,
            onValueChanged = { description = it },
            16,
            onDeleteClick = {description = ""}
        )

        EditElement(text = "Images", value = images, onValueChanged = { images = it }, 16,  onDeleteClick = {images = ""})
    }
}