package com.example.onlinestore.views.search_screen

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


@Composable
fun SearchScreen(controller: NavController, model: StoreViewModel) {
    val input by model.searchSting.collectAsState()
    val sItems by model.historyList.collectAsState()
    val productList by model.productsOnSearch.collectAsState()
    if (input != "") {
        model.loadProductByName(input)
        ProductItem2(
            productList,
            model,
            controller
        )
    }
    else{
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
                    text = "Last search",
                    fontWeight = FontWeight(500),
                    fontSize = (21.sp),
                    color = colorResource(id = R.color.gray_search)
                )

                Text(
                    text = "Clear all",
                    Modifier.clickable { },
                    color = colorResource(id = R.color.red_search),
                    fontWeight = FontWeight(500),
                    fontSize = (16.sp)
                )
            }

            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(sItems.reversed()) { item ->
                    HistoryListItem(item = item,
                        onDeleteClick = {

                        })
                }
            }
        }
    }
}
