package com.example.onlinestore.views.manager_screen.delete_category

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.example.onlinestore.R
import com.example.onlinestore.core.StoreViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeleteCategory(viewModel: StoreViewModel) {

    val categories by viewModel.categories.collectAsState()
    var searchQueryState by remember { mutableStateOf("") }
    var isActive by remember { mutableStateOf(false) }
    var isActiveButton by remember { mutableStateOf(false) }
    var itemId by remember { mutableStateOf(0) }
    val context = LocalContext.current


    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column() {
            SearchBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                query = searchQueryState,
                onQueryChange = { text ->
                    searchQueryState = text
                },
                placeholder = { Text("Search...") },
                active = isActive,
                onActiveChange = {
                    isActive = it
                },
                onSearch = { text ->
                    isActive = false

                },
            ) {
                LazyColumn {
                    items(categories.filter {
                        it.name.lowercase().startsWith(searchQueryState.lowercase())
                    }) { item ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp)
                                .clickable {
                                    searchQueryState = item.name
                                    itemId = item.id
                                    isActive = false
                                },
                            contentAlignment = Alignment.Center,

                            ) {
                            Text(item.name)
                        }

                    }

                }
            }
            if (itemId != 0) {
                isActiveButton = true
            }

            Button(
                modifier = Modifier
                    .height(56.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.Green_Sheen)
                ),
                enabled = isActiveButton,
                onClick = {
                    viewModel.deleteCategory(itemId,context)
                    searchQueryState = ""
                    itemId = 0
                    isActiveButton = false
                          },
            ) {
                Text("Delete product")
            }
        }
    }
}
