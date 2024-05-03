package com.example.onlinestore.views.HomeScreen

import android.widget.ImageButton
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem

import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.onlinestore.R
import com.example.onlinestore.navigation.Screen
import com.example.onlinestore.views.HomeScreen.network.model.Category
import com.example.onlinestore.views.HomeScreen.network.model.ProductItem
import kotlinx.coroutines.launch

@Composable
fun MainScreen(
    controller: NavController,
    navigateToDetail: (ProductItem) -> Unit
) {
    val viewModel: ProductViewModelTest = viewModel()
    val viewState by viewModel.productState
    var expended by remember { mutableStateOf(false) }
    val productFilter = listOf("price up", "price down", "title A","title Z")
    val productList= viewState.list?.toMutableStateList()
    val categoryState by viewModel.categoryState
    val categoryList = categoryState.list?.toMutableStateList()


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 20.dp, end = 20.dp, top = 20.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column() {
                Text(
                    "Delivery address",
                    fontSize = 10.sp,
                    color = colorResource(R.color.Grey),

                    )
                TextFieldDropDownMenu()
            }
            Row(
                Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton({
                    controller.navigate(Screen.NavigationItem.Cart.route)
                }) {
                    Icon(painter = painterResource(R.drawable.buy), "")
                }
                Icon(painter = painterResource(R.drawable.notification), "")
            }

        }
        Spacer(Modifier.padding(top = 20.dp))
        SearchBar2(controller)

        Row(
            Modifier
                .fillMaxWidth()
                .padding(top = 30.dp)
        ) {
            categoryList?.let {
                repeat(4) {
                    CategoryButtonItem(categoryList, it)
                }
                AllCategoryButton(categoryList)
            }
        }
        Row(
            Modifier
                .fillMaxWidth()
                .padding(top = 30.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Products", fontSize = 25.sp)

            Button(
                {
                    expended = !expended
                },
                colors = ButtonDefaults.buttonColors(Color.Transparent),
                modifier = Modifier
                    .border(
                        1.dp,
                        shape = RoundedCornerShape(10.dp),
                        color = colorResource(R.color.buttonBorder)
                    )
                    .height(40.dp),
                elevation = ButtonDefaults.elevation(0.dp)

            ) {
                Text(
                    "Filters",
                    color = colorResource(R.color.Dark_Arsenic),
                    modifier = Modifier.padding(end = 3.dp)
                )
                Icon(painter = painterResource(R.drawable.outline_filter_alt_24), "")
                DropdownMenu(
                    expanded = expended,
                    onDismissRequest = {
                        expended = false
                    },
                ) {
                    productFilter.forEach {
                        DropdownMenuItem(text = { Text(it) }, onClick = {
                            when (it) {
                                "price up" -> productList?.sortBy { it.price }
                                "price down" -> productList?.sortByDescending { it.price }
                                "title A" -> productList?.sortBy { it.title }
                                "title Z" -> productList?.sortByDescending { it.title }
                            }
                            expended = false
                        })
                    }

                }
            }
        }
        when {
            viewState.loading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Start),
                    color = Color.Black
                )
            }

            else -> {
                ProductItem2(productList, navigateToDetail)
            }
        }
    }
}


@Composable
fun ProductItem2(
    list: SnapshotStateList<ProductItem>?,
    navigateToDetail: (ProductItem) -> Unit
) {
    LazyVerticalGrid(columns = GridCells.Fixed(2), state = rememberLazyGridState()) {
        list?.let {
            items(list) { items ->
                CardItem(items, navigateToDetail)

            }
        }
    }
}

@Composable
fun CategoryButtonItem(list2: SnapshotStateList<Category>, it: Int) {
    Column(
        Modifier
            .padding(end = 18.dp), horizontalAlignment =
        Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = list2[it].image, "",
            modifier = Modifier
                .size(60.dp)
                .clip(RoundedCornerShape(10.dp))
                .clickable {
                    TODO()
                },
        )
        Text(list2[it].name.toString(), fontSize = 10.sp)
    }
}

@Composable
fun AllCategoryButton(list2: SnapshotStateList<Category>) {
    var expended by remember { mutableStateOf(false) }
    IconButton({
        expended = !expended
    }) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painterResource(R.drawable.group_8__3_),
                "",
                modifier = Modifier.size(60.dp)
            )
            Text("All", fontSize = 10.sp)
        }
        DropdownMenu(
            expanded = expended,

            onDismissRequest = {
                expended = false
            }) {
            list2.distinctBy { it.name }.forEach { item ->
                DropdownMenuItem(text = { Text(item.name.toString()) }, {})
            }
        }

    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SearchBar2(
    controller: NavController
) {
    val focusManager = LocalFocusManager.current
    val interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
    BasicTextField(
        value = "",
        onValueChange = {},
        modifier = Modifier
            .background(Color.White)
            .fillMaxWidth()
            .height(40.dp)
            .border(1.dp, Color.LightGray, RoundedCornerShape(10.dp))
            .onFocusEvent {
                if (it.isFocused) {
                    controller.navigate(Screen.NavigationItem.SearchResultScreen.tRoute)
                    focusManager.clearFocus()
                }
            },
        readOnly = true,

        textStyle = TextStyle(fontSize = 13.sp),
    ) {
        TextFieldDefaults.TextFieldDecorationBox(
            value = "",
            innerTextField = it,
            singleLine = true,
            enabled = true,
            interactionSource = interactionSource,
            visualTransformation = VisualTransformation.None,
            placeholder = {
                Text(
                    "Search here...",
                    color = colorResource(R.color.Grey),
                    fontSize = 13.sp
                )
            },
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.search),
                    "",
                    tint = Color.Gray
                )
            },
            contentPadding = TextFieldDefaults.textFieldWithLabelPadding(top = 0.dp, bottom = 0.dp)
        )
    }
}

@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class
)
@Composable
fun TextFieldDropDownMenu() {
    val options =
        listOf(
            "Москва, ул.Алкашей",
            "Санкт-Петербург, Софийская улица, 6 ",
            "Berlin, hitler street"
        )
    var expanded by remember { mutableStateOf(false) }
    var text by remember { mutableStateOf(options[0]) }
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        Row() {
            Text(text)
            Icon(
                Icons.Filled.KeyboardArrowDown,
                null,
                Modifier.rotate(if (expanded) 180f else 0f)
            )
        }
        if (expanded) {
            ModalBottomSheet(
                onDismissRequest = {
                    expanded = false
                },
                modifier = Modifier
                    .fillMaxHeight(0.5f)
                    .navigationBarsPadding(),
                containerColor = Color.White,
                sheetState = sheetState
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(15.dp)
                ) {
                    Text(
                        "Delivery address",
                        color = colorResource(R.color.Dark_Arsenic),
                        fontSize = 28.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(options) { adress ->
                            Card(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(top = 10.dp),
                                colors = CardDefaults.cardColors(Color.White),
                                onClick = {
                                    text = adress
                                    scope.launch {
                                        sheetState.hide()
                                    }.invokeOnCompletion {
                                        if (!sheetState.isVisible)
                                            expanded = false
                                    }
                                }
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        adress,
                                        color = colorResource(R.color.Dark_Arsenic),
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 20.sp
                                    )
                                    if (adress == text)
                                        Icon(
                                            Icons.Default.Check,
                                            "",
                                            tint = colorResource(R.color.Green_Sheen)
                                        )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
