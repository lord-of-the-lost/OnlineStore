package com.example.onlinestore.views.search_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.onlinestore.R
import com.example.onlinestore.core.StoreViewModel
import com.example.onlinestore.views.HomeScreen.ProductItem2

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun SearchScreen(controller: NavController, model: StoreViewModel) {
    val items by model.historyList.collectAsState()
    val input by model.searchSting.collectAsState()
    val productList by model.productsOnSearch.collectAsState()

    LaunchedEffect(key1 = true) {
        model.initializeSearchHistory()
    }

    if (input.isNotEmpty()) {
        LaunchedEffect(key1 = input) {
            model.loadProductByName(input)
        }
        if (productList.isNotEmpty()) {
            ProductItem2(productList, model, controller)
        } else {
            Text("По вашему запросу ничего не найдено", modifier = Modifier.padding(16.dp))
        }
    } else {
        model.clearProductSearch()
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "История поиска",
                    fontWeight = FontWeight(500),
                    fontSize = 21.sp,
                    color = colorResource(id = R.color.gray_search)
                )

                if (items.isNotEmpty()) {
                    Text(
                        text = "Очистить всё",
                        Modifier.clickable {
                            model.deleteAllHistory()
                        },
                        color = colorResource(id = R.color.red_search),
                        fontWeight = FontWeight(500),
                        fontSize = 16.sp
                    )
                }
            }

            if (items.isEmpty()) {
                Text("Нет истории поиска", modifier = Modifier.padding(16.dp))
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(items.reversed()) { item ->
                        HistoryListItem(
                            item = item,
                            onDeleteClick = {
                                model.deleteHistory(item)
                            }, model
                        )
                    }
                }
            }
        }
    }
}