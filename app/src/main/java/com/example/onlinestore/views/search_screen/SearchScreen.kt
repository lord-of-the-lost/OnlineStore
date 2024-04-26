package com.example.onlinestore.views.search_screen

import com.example.onlinestore.R

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SearchScreen() {

    var text by remember {
        mutableStateOf("")
    }
    var sItems by remember {
        mutableStateOf(listOf<HistoryItem>())
    }


    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(modifier = Modifier.padding(18.dp)) {
            OutlinedTextField(value = text, onValueChange = { text = it })
            IconButton(onClick = {
                if (text.isNotEmpty()) {
                    val newItem = HistoryItem(
                        name = text,
                        id = sItems.size + 1
                    )
                    sItems = sItems + newItem
                    text = ""
                }
            }) {
                Icon(imageVector = Icons.Default.Search, contentDescription = null)
            }
        } // Убрать в будущем

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Last search",
                fontWeight = FontWeight(500),
                fontSize = (21.sp),
                color = colorResource(id = R.color.gray_search)
            )

            Text(
                text = "Clear all",
                Modifier.clickable { sItems = sItems - sItems },
                color = colorResource(id = R.color.red_search),
                fontWeight = FontWeight(500),
                fontSize = (16.sp)
            )
        }

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(sItems.reversed()) { item ->
                HistoryListItem(item = item,
                    onDeleteClick = {
                        sItems = sItems - item
                    })
            }
        }
    }
}
