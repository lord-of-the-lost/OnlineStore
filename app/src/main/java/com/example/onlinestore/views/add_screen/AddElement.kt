package com.example.onlinestore.views.add_screen

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.onlinestore.R

@Composable
fun Element(
    text: String,
    value: String,
    onValueChanged: (String) -> Unit,
    textSize: Int
) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .width(120.dp)
                .padding(top = 28.dp, start = 16.dp)
        ) {
            Text(
                text = text,
                fontSize = 17.sp,
                fontWeight = FontWeight.W700,
                color = Color.Black
            )
        }


        OutlinedTextField(
            modifier = Modifier.padding(top = 12.dp, start = 10.dp, end = 10.dp),
            value = value,
            onValueChange = onValueChanged,
            textStyle = TextStyle(
                color = colorResource(id = R.color.gray_search),
                fontSize = textSize.sp,
                fontWeight = FontWeight.W700
            ),
            shape = RoundedCornerShape(11.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = colorResource(id = R.color.gray_border),
                unfocusedBorderColor = colorResource(id = R.color.gray_border),
                cursorColor = colorResource(id = R.color.black),
                focusedLabelColor = colorResource(id = R.color.gray_border),
                unfocusedLabelColor = colorResource(id = R.color.gray_border),
            )
        )
    }
}


@Composable
fun ElementDropDown(
    text1: String,
    text2: String,
    text3: String,
    text4: String,
) {
    var expanded by remember {
        mutableStateOf(false)
    }

    var textMenu by remember {
        mutableStateOf("Category")
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .width(120.dp)
                .padding(top = 28.dp, start = 16.dp)
        ) {
            Text(
                text = "Category",
                fontSize = 17.sp,
                fontWeight = FontWeight.W700,
                color = colorResource(id = R.color.gray_search)
            )
        }


        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp, start = 10.dp, end = 10.dp)
                .clickable { expanded = true }
                .border(
                    1.dp,
                    color = colorResource(id = R.color.gray_border),
                    RoundedCornerShape(11.dp)
                )
                .width(328.dp)
                .height(52.dp),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = textMenu,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.W700,
                    color = colorResource(id = R.color.gray_search)
                )
                Icon(imageVector = Icons.Default.KeyboardArrowDown, contentDescription = null) //изменить дропдаун меню

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    Modifier.width(200.dp)
                ) {
                    DropdownMenuItem(
                        onClick = {
                            textMenu = text1
                            expanded = false
                        },
                        text = { Text(text1) }
                    )
                    DropdownMenuItem(
                        onClick = {
                            textMenu = text2
                            expanded = false
                        },
                        text = { Text(text2) }
                    )
                    DropdownMenuItem(
                        onClick = {
                            textMenu = text3
                            expanded = false
                        },
                        text = { Text(text3) }
                    )
                    DropdownMenuItem(
                        onClick = {
                            textMenu = text4
                            expanded = false
                        },
                        text = { Text(text4) }
                    )
                }
            }
        }
    }
}


