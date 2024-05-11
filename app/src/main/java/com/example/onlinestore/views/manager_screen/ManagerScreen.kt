package com.example.onlinestore.views.manager_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.onlinestore.navigation.Screen


@Composable
fun ManagerScreen(
    modifier: Modifier = Modifier,
    controller: NavController
) {
    Box(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 20.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AddButtons("Add new product") { controller.navigate(Screen.NavigationItem.AddProduct.route) }
            AddButtons("Update product") { controller.navigate(Screen.NavigationItem.UpdateProduct.route)}
            AddButtons("Delete product") { controller.navigate(Screen.NavigationItem.DeleteProduct.route)}
            AddButtons("Create category") { }
            AddButtons("Update category") { }
            AddButtons("Delete category") { }
        }
    }
}

@Composable
fun AddButtons(text: String, function: () -> Unit) {
    Box(
        contentAlignment = Alignment.CenterStart,
        modifier = Modifier
            .width(308.dp)
            .height(50.dp)
            .background(color = Color(0xFF67C4A7), shape = RoundedCornerShape(4.dp))
            .clickable { function() }
            .padding(horizontal = 15.5.dp, vertical = 8.dp)
    ) {

        Text(
            text = text,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            color = Color.White
        )
    }
}

