package com.example.onlinestore.views.manager_screen.update_category

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.onlinestore.R
import com.example.onlinestore.core.StoreViewModel
import com.example.onlinestore.core.models.RequestModel.PostCategoryModel
import com.example.onlinestore.views.manager_screen.add_screen.Element

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateCategory(viewModel: StoreViewModel) {
    val categories by viewModel.categories.collectAsState()
    var name by remember { mutableStateOf("") }
    var newName by remember { mutableStateOf("") }
    var isActive by remember { mutableStateOf(false) }
    var isActiveButton by remember { mutableStateOf(false) }
    var itemId by remember { mutableStateOf(0) }
    var image by remember { mutableStateOf("") }
    val category by remember { mutableStateOf(PostCategoryModel()) }
    val context = LocalContext.current

    Column(
        Modifier
            .fillMaxSize()

    ) {
        SearchBar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
                .border(width = 1.dp, shape = RoundedCornerShape(8.dp), color = colorResource(R.color.custom_grey)),
            query = newName,
            onQueryChange = { text ->
                newName = text
            },
            placeholder = { Text("Search...") },
            active = isActive,
            onActiveChange = {
                isActive = it
            },
            onSearch = { text ->
                isActive = false
            },
            leadingIcon = {
                Icon(painter = painterResource(R.drawable.search),"search", tint = colorResource(R.color.Grey))
            },
            shape = RoundedCornerShape(8.dp),
            colors = SearchBarDefaults.colors(
                containerColor = Color.White,
                dividerColor = colorResource(R.color.gray_search)

            )
        ) {
            LazyColumn {
                items(categories.filter {
                    it.name.lowercase().startsWith(newName.lowercase())
                }) { item ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                            .clickable {
                                newName = item.name
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
        Element(value = name, onValueChanged = { name = it }, text = "Name", textSize = 17)
        Spacer(modifier = Modifier.height(32.dp))
        Element(value = image, onValueChanged = { image = it }, text = "Image", textSize = 17)
        Spacer(modifier = Modifier.height(32.dp))
        if (itemId != 0) {
            isActiveButton = true
            category.name = if(name == "") null else name
            category.image = if(image == "") null else if(!isValidUrl(image)) null else image
        }

        Button(
            onClick =
            {
                viewModel.updateCategory(itemId, category,context)
            },
            modifier = Modifier
                .height(56.dp)
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.Green_Sheen),
            ),
            enabled = isActiveButton
        ) {
            Text(
                "Update Category",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
fun isValidUrl(url: String): Boolean {
    val regex = Regex("https?://\\S+")
    return regex.matches(url)
}