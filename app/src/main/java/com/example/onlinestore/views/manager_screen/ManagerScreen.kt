package com.example.onlinestore.views.manager_screen

import androidx.compose.foundation.Image
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
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.onlinestore.R


@Composable
fun ManagerScreen(
    modifier: Modifier = Modifier,

) {
    Box(modifier = modifier){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 100.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                contentAlignment = Alignment.CenterStart,
                modifier = Modifier
                    .width(308.dp)
                    .height(50.dp)
                    .background(color = Color(0xFF67C4A7), shape = RoundedCornerShape(4.dp))
                    .clickable(onClick = {})
                    .padding(horizontal = 15.5.dp, vertical = 8.dp)
            ) {

                Text(
                    text = "Add new product",
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    color = Color.White
                )
            }
            Box(
                contentAlignment = Alignment.CenterStart,
                modifier = Modifier
                    .width(308.dp)
                    .height(50.dp)
                    .background(color = Color(0xFF67C4A7), shape = RoundedCornerShape(4.dp))
                    .clickable(onClick = {})
                    .padding(horizontal = 15.5.dp, vertical = 8.dp)
            ) {

                Text(
                    text = "Update product",
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    color = Color.White
                )
            }
            Box(
                contentAlignment = Alignment.CenterStart,
                modifier = Modifier
                    .width(308.dp)
                    .height(50.dp)
                    .background(color = Color(0xFF67C4A7), shape = RoundedCornerShape(4.dp))
                    .clickable(onClick = {})
                    .padding(horizontal = 15.5.dp, vertical = 8.dp)
            ) {

                Text(
                    text = "Delete product",
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    color = Color.White
                )
            }
            Box(
                contentAlignment = Alignment.CenterStart,
                modifier = Modifier
                    .width(308.dp)
                    .height(50.dp)
                    .background(color = Color(0xFF67C4A7), shape = RoundedCornerShape(4.dp))
                    .clickable(onClick = {})
                    .padding(horizontal = 15.5.dp, vertical = 8.dp)
            ) {

                Text(
                    text = "Create category",
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    color = Color.White
                )
            }
            Box(
                contentAlignment = Alignment.CenterStart,
                modifier = Modifier
                    .width(308.dp)
                    .height(50.dp)
                    .background(color = Color(0xFF67C4A7), shape = RoundedCornerShape(4.dp))
                    .clickable(onClick = {})
                    .padding(horizontal = 15.5.dp, vertical = 8.dp)
            ) {

                Text(
                    text = "Update category",
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    color = Color.White
                )
            }
            Box(
                contentAlignment = Alignment.CenterStart,
                modifier = Modifier
                    .width(308.dp)
                    .height(50.dp)
                    .background(color = Color(0xFF67C4A7), shape = RoundedCornerShape(4.dp))
                    .clickable(onClick = {})
                    .padding(horizontal = 15.5.dp, vertical = 8.dp)
            ) {

                Text(
                    text = "Delete category",
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    color = Color.White
                )
            }

        }
    }
}


@Preview(showBackground = true)
@Composable
fun ManagerPreview() {
    ManagerScreen()
}