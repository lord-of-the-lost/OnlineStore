package com.example.onlinestore.views.search_screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.onlinestore.core.StoreViewModel
import com.example.onlinestore.views.HomeScreen.ProductItem2


@Composable
fun SearchScreen(controller: NavController) {
    val model:StoreViewModel = viewModel()
    val productList by model.products.collectAsState()
    model.sortByName()


    ProductItem2(productList,model,controller)

//    var text by remember {
//        mutableStateOf("")
//    }
//    var sItems by remember {
//        mutableStateOf(listOf<HistoryItem>())
//    }






















//    Column(
//        modifier = Modifier
//            .fillMaxWidth(),
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Row(modifier = Modifier.padding(18.dp)) {
//            OutlinedTextField(value = text, onValueChange = { text = it })
//            IconButton(onClick = {
//                if (text.isNotEmpty()) {
//                    val newItem = HistoryItem(
//                        name = text,
//                        id = sItems.size + 1
//                    )
//                    sItems = sItems + newItem
//                    text = ""
//                }
//            }) {
//                Icon(imageVector = Icons.Default.Search, contentDescription = null)
//            }
//        } // Убрать в будущем
//
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(16.dp),
//            horizontalArrangement = Arrangement.SpaceBetween,
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            Text(
//                text = "Last search",
//                fontWeight = FontWeight(500),
//                fontSize = (21.sp),
//                color = colorResource(id = R.color.gray_search)
//            )
//
//            Text(
//                text = "Clear all",
//                Modifier.clickable { sItems = sItems - sItems },
//                color = colorResource(id = R.color.red_search),
//                fontWeight = FontWeight(500),
//                fontSize = (16.sp)
//            )
//        }
//
//        LazyColumn(modifier = Modifier.fillMaxSize()) {
//            items(sItems.reversed()) { item ->
//                HistoryListItem(item = item,
//                    onDeleteClick = {
//                        sItems = sItems - item
//                    })
//            }
//        }
    }

//}
