package com.example.onlinestore.views.HomeScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.onlinestore.R
import com.example.onlinestore.core.StoreViewModel
import com.example.onlinestore.core.models.CategoryModel
import com.example.onlinestore.core.models.ProductModel
import com.example.onlinestore.navigation.Screen
import kotlinx.coroutines.launch

@Composable
fun MainScreen(
    controller: NavController,
    viewModel: StoreViewModel
) {
    var expended by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }
    val productFilter = listOf("price up", "price down", "title A", "title Z", "price range")
    val size by viewModel.cartSize.collectAsState()
    val productList by viewModel.products.collectAsState()
    val categoryList by viewModel.categories.collectAsState()

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
                TextFieldDropDownMenu(viewModel)
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
                    Icon(
                        painter = painterResource(R.drawable.buy),
                        "",
                        tint = colorResource(R.color.Dark_Arsenic)
                    )
                    Box(modifier = Modifier.padding(bottom = 25.dp, start = 15.dp)) {
                        CartSize(size)
                    }

                }


                Icon(
                    painter = painterResource(R.drawable.notification),
                    "",
                    tint = colorResource(R.color.Dark_Arsenic)
                )
            }

        }
        Spacer(Modifier.padding(top = 20.dp))
        SearchBar2(controller)

        Row(
            Modifier
                .fillMaxWidth()
                .padding(top = 30.dp)
        ) {
            categoryList.take(4).forEach { category ->
                CategoryButtonItem(category, Modifier.weight(1f), viewModel)
            }
            AllCategoryButton(categoryList, Modifier.weight(1f), viewModel)
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
                                "price up" -> viewModel.sortProductsByPriceAscending()
                                "price down" -> viewModel.sortProductsByPriceDescending()
                                "title A" -> viewModel.sortProductsByTitleAscending()
                                "title Z" -> viewModel.sortProductsByTitleDescending()
                                "price range" -> showDialog = true
                            }
                            expended = false
                        })
                    }

                }
            }
        }
        if (showDialog) {
            PriceRangeDialog(viewModel) {
                showDialog = it
            }
        }
        ProductItem2(productList, viewModel, controller)
    }
}

@Composable
fun ProductItem2(
    list: List<ProductModel>?,
    viewModel: StoreViewModel,
    navController: NavController
) {
    LazyVerticalGrid(columns = GridCells.Fixed(2), state = rememberLazyGridState()) {
        list?.let {
            items(list) { item ->
                CardItem(item, viewModel, navController)
            }
        }
    }
}

@Composable
fun CategoryButtonItem(
    category: CategoryModel,
    modifier: Modifier = Modifier,
    viewModel: StoreViewModel
) {
    Column(
        modifier = modifier
            .padding(end = 18.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = category.image,
            contentDescription = "",
            modifier = Modifier
                .size(60.dp)
                .clip(RoundedCornerShape(10.dp))
                .clickable {
                    viewModel.loadProductsByCategoryId(category.id)
                },
        )
        Text(category.name, fontSize = 10.sp)
    }
}

@Composable
fun AllCategoryButton(
    list2: List<CategoryModel>,
    modifier: Modifier = Modifier,
    viewModel: StoreViewModel
) {
    var expended by remember { mutableStateOf(false) }
    IconButton({
        expended = !expended
    }) {
        Column(modifier, horizontalAlignment = Alignment.CenterHorizontally) {
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
                DropdownMenuItem(text = { Text(item.name) }, { viewModel.loadProductsByCategoryId(item.id) })
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

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun TextFieldDropDownMenu(viewModel: StoreViewModel) {
    val options = listOf(
        "Америка",
        "Россия",
        "Европа"
    )
    var expanded by remember { mutableStateOf(false) }
    val selectedCountry by viewModel.selectedCountry.collectAsState()
    var text by remember { mutableStateOf(selectedCountry) }
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        Row(modifier = Modifier.clickable { expanded = true }) {
            Text(text)
            Icon(
                Icons.Filled.KeyboardArrowDown,
                contentDescription = "Dropdown Arrow",
                Modifier.rotate(if (expanded) 180f else 0f)
            )
        }
        if (expanded) {
            ModalBottomSheet(
                onDismissRequest = { expanded = false },
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
                        items(options) { option ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 10.dp),
                                colors = CardDefaults.cardColors(Color.White),
                                onClick = {
                                    text = option
                                    scope.launch {
                                        sheetState.hide()
                                    }.invokeOnCompletion {
                                        if (!sheetState.isVisible) {
                                            expanded = false
                                            val actualCountry =
                                                option.replace("Текущая локация: ", "")
                                            viewModel.setSelectedCountry(actualCountry)
                                        }
                                    }
                                }
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        option,
                                        color = colorResource(R.color.Dark_Arsenic),
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 20.sp
                                    )
                                    if (option == text)
                                        Icon(
                                            Icons.Default.Check,
                                            contentDescription = "Selected",
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

@Composable
fun CartSize(size: Int) {
    if (size > 0) {
        Image(
            painterResource(R.drawable.ellipse_5),
            "",
            modifier = Modifier
                .padding(top = 8.dp)
                .size(16.dp)
        )
        Text(
            text = size.toString(),
            color = Color.White,
            fontSize = 12.sp,
            modifier = Modifier
                .padding(top = 8.dp, start = if(size >= 10) 0.dp else 4.dp)
        )
    }
}

@Composable
fun PriceRangeDialog(viewModel: StoreViewModel, showDialog: (Boolean) -> Unit) {
    val minPrice = viewModel.minPrice.collectAsState().value
    val maxPrice = viewModel.maxPrice.collectAsState().value
    val currency = viewModel.currentCurrency.collectAsState().value

    val initialMin = viewModel.selectedMinPrice.collectAsState().value ?: minPrice
    val initialMax = viewModel.selectedMaxPrice.collectAsState().value ?: maxPrice
    val (range, setRange) = remember { mutableStateOf(initialMin..initialMax) }

    AlertDialog(
        onDismissRequest = { showDialog(false) },
        title = { Text(text = "Select Price Range") },
        text = {
            Column {
                val formattedMin = viewModel.formatPriceWithCurrency(range.start.toDouble(), currency)
                val formattedMax = viewModel.formatPriceWithCurrency(range.endInclusive.toDouble(), currency)
                Text("Current range: $formattedMin - $formattedMax")
                RangeSlider(
                    value = range,
                    onValueChange = { newRange -> setRange(newRange) },
                    valueRange = minPrice..maxPrice,
                    steps = 0
                )
            }
        },
        confirmButton = {
            Button(onClick = {
                viewModel.filterProductsByPriceRange(range.start, range.endInclusive)
                showDialog(false)
            }) {
                Text("Apply")
            }
        },
        dismissButton = {
            Button(onClick = { showDialog(false) }) {
                Text("Cancel")
            }
        }
    )
}
