package com.example.onlinestore.views.add_screen

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
import androidx.compose.ui.unit.dp
import com.example.onlinestore.navigation.Screen

@Composable
fun AddProduct() {
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

        Element(text = "Title", value = title, onValueChanged = {title = it}, 17)

        Spacer(modifier = Modifier.height(32.dp))

        Element(text = "Price", value = price, onValueChanged = {price = it},17)

        Spacer(modifier = Modifier.height(8.dp))

        ElementDropDown("1", "2", "3", "4")

        Spacer(modifier = Modifier.height(8.dp))

        Element(text = "Description", value = description, onValueChanged = {description = it}, 16)

        Element(text = "Images", value = images, onValueChanged = {images = it}, 16)
    }
}