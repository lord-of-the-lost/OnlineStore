package com.example.onlinestore.views.manager_screen.update_Screen

import android.widget.Toast
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
import com.example.onlinestore.core.models.RequestModel.PostProductModel
import com.example.onlinestore.views.manager_screen.add_screen.Element
import com.example.onlinestore.views.manager_screen.add_screen.ElementDropDown


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateScreen(viewModel: StoreViewModel) {

    val categoryList by viewModel.categories.collectAsState()
    val productList by viewModel.products.collectAsState()
    val categoryId by viewModel.categoryId.collectAsState()
    var searchQueryState by remember { mutableStateOf("") }
    var isActive by remember { mutableStateOf(false) }
    var itemId by remember { mutableStateOf(0) }


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
        SearchBar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
                .border(
                    width = 1.dp,
                    shape = RoundedCornerShape(8.dp),
                    color = colorResource(R.color.custom_grey)
                ),
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
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.search),
                    "search",
                    tint = colorResource(R.color.Grey)
                )
            },
            shape = RoundedCornerShape(8.dp),
            colors = SearchBarDefaults.colors(
                containerColor = Color.White,
                dividerColor = colorResource(R.color.gray_search)
            )

        ) {
            LazyColumn {
                items(productList.filter {
                    it.title.lowercase().startsWith(searchQueryState.lowercase())
                }) { item ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                            .clickable {
                                searchQueryState = item.title
                                itemId = item.id
                                isActive = false
                            },
                        contentAlignment = Alignment.Center,

                        ) {
                        Text(item.title)
                    }

                }

            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Element(text = "Title", value = title, onValueChanged = { title = it }, 17)

        Spacer(modifier = Modifier.height(32.dp))

        Element(text = "Price", value = price, onValueChanged = { price = it }, 17)

        Spacer(modifier = Modifier.height(8.dp))

        ElementDropDown(categoryList, viewModel)

        Spacer(modifier = Modifier.height(8.dp))

        Element(
            text = "Description",
            value = description,
            onValueChanged = { description = it },
            17
        )

        Element(text = "Images", value = images, onValueChanged = { images = it }, 16)


        val context = LocalContext.current
        val image: List<String> = images.split(" ")
        Spacer(modifier = Modifier.padding(top = 40.dp))

        Button(
            onClick =
            {
                viewModel.updateProduct(
                    itemId, PostProductModel(
                        if (title == "") null else title,
                        if (price == "") null else price.toInt(),
                        if (description == "") null else description,
                        if (categoryId == 0) null else categoryId,
                        if (image[0] == "") null else image
                    )
                )
                Toast.makeText(context, "Success Update", Toast.LENGTH_LONG).show()
            },
            modifier = Modifier
                .height(56.dp)
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.Green_Sheen),
            )
        ) {
            Text(
                "Update Product",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}






