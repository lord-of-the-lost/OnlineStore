package com.example.onlinestore.views.CartScreen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.onlinestore.R
import com.example.onlinestore.core.StoreViewModel
import com.example.onlinestore.ui.theme.inter
import kotlinx.coroutines.launch
import kotlin.random.Random

data class ShopItem(
    val productId: Int,
    val imgOfProduct: String?,
    val nameOfProduct: String,
    val priceOfProduct: String,
    var quantity: Int,
    var isSelected: Boolean = false
)

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("MutableCollectionMutableState")
@Composable
fun CartScreen(viewModel: StoreViewModel) {
    val shopItemsInCart by viewModel.shopItemsInCart.collectAsState()
    var totalPrice by remember { mutableStateOf(0.0) }
    var isAtLeastOneItemSelected by remember { mutableStateOf(false) }

    LaunchedEffect(shopItemsInCart) {
        totalPrice = shopItemsInCart.filter { it.isSelected }.sumOf {
            parsePrice(it.priceOfProduct) * it.quantity
        }
        isAtLeastOneItemSelected = shopItemsInCart.any { it.isSelected }
    }

    fun updateTotalPrice() {
        totalPrice = shopItemsInCart.filter { it.isSelected }.sumOf {
            parsePrice(it.priceOfProduct) * it.quantity
        }
    }

    Column {
        DeliveryAddress()
        Box(
            modifier = Modifier
                .border(1.dp, Color(0xFFD9D9D9))
                .fillMaxWidth()
                .size(0.dp, 1.dp)
        )
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(start = 20.dp, end = 20.dp)
        ) {
            items(shopItemsInCart) { shopItem ->
                ShoppingListItem(
                    shopItem = shopItem,
                    viewModel = viewModel,
                    onDeleteItem = {
                        viewModel.removeFromCart(shopItem.productId)
                    },
                    onItemSelectedChanged = { isSelected ->
                        totalPrice = shopItemsInCart.filter { it.isSelected }.sumOf {
                            parsePrice(it.priceOfProduct) * it.quantity
                        }
                        isAtLeastOneItemSelected = shopItemsInCart.any { it.isSelected }
                    },
                    onQuantityChange = { newQuantity ->
                        shopItem.quantity = newQuantity
                        updateTotalPrice()
                    }
                )
            }
        }
        Box(
            modifier = Modifier
                .border(1.dp, Color(0xFFD9D9D9))
                .fillMaxWidth()
                .size(0.dp, 1.dp)
        )
        Column(
            modifier = Modifier
                .padding(start = 20.dp, end = 20.dp)
        ) {
            OrderSummary(
                totalPrice,
                isAtLeastOneItemSelected,
                viewModel,
                shopItemsInCart.filter { it.isSelected == true })
        }
    }
}

@Composable
fun DeliveryAddress() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .size(0.dp, 44.dp)
            .padding(start = 20.dp, end = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Delivery to",
            style = TextStyle(
                fontFamily = inter,
                fontWeight = FontWeight.SemiBold,
                fontSize = 12.sp,
                lineHeight = 16.94.sp,
                color = Color(0xFF393F42),
            )
        )
        DropDownExample()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDownExample() {
    val options = listOf(
        "Кудыкина Гора, пещера №5",
        "г.Оренбург, ул.Советская",
        "г.Иркутск, ул.Космическая"
    )
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf(options[0]) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
    ) {
        TextField(
            modifier = Modifier
                .menuAnchor()
                .wrapContentSize()
                .offset(x = 18.dp),
            readOnly = true,
            value = selectedOptionText,
            onValueChange = {},
            textStyle = TextStyle(
                fontFamily = inter,
                fontWeight = FontWeight.SemiBold,
                fontSize = 12.sp,
                lineHeight = 16.94.sp,
                color = Color(0xFF393F42),
                textAlign = TextAlign.End
            ),
            singleLine = true,
            trailingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.drop_down_icon),
                    contentDescription = null,
                    modifier = Modifier
                        .size(12.dp)
                        .rotate(if (expanded) 180f else 0f)
                )
            },
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                unfocusedTextColor = Color(0xff888888),
                focusedContainerColor = Color.White,
                focusedTextColor = Color(0xff222222),
                unfocusedIndicatorColor = Color.White,
                focusedIndicatorColor = Color.White
            )
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.background(Color.White)

        ) {
            options.forEach { selectionOption ->
                DropdownMenuItem(
                    text = {
                        Text(
                            textAlign = TextAlign.End,
                            modifier = Modifier.fillMaxWidth(),
                            text = selectionOption
                        )
                    },
                    onClick = {
                        selectedOptionText = selectionOption
                        expanded = false
                    },
                )
            }
        }
    }
}

@Composable
fun ShoppingListItem(
    shopItem: ShopItem,
    viewModel: StoreViewModel,
    onDeleteItem: (ShopItem) -> Unit,
    onItemSelectedChanged: (Boolean) -> Unit,
    onQuantityChange: (Int) -> Unit
) {
    var isSelected by remember { mutableStateOf(shopItem.isSelected) }

    LaunchedEffect(shopItem.isSelected) {
        isSelected = shopItem.isSelected
    }

    val currentCurrency by viewModel.currentCurrency.collectAsState()
    val price = parsePrice(shopItem.priceOfProduct)
    val formattedPrice = viewModel.formatPriceWithCurrency(price, currentCurrency)


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 22.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(modifier = Modifier
            .size(24.dp),
            onClick = {
                isSelected = !isSelected
                shopItem.isSelected = isSelected
                onItemSelectedChanged(isSelected)
            }) {
            val iconTint = if (isSelected) Color(0xFF67C4A7) else Color(0xFFD9D9D9)
            Icon(
                modifier = Modifier
                    .size(24.dp),
                imageVector = ImageVector.vectorResource(
                    if (isSelected)
                        R.drawable.checked_icon
                    else
                        R.drawable.unchecked_icon
                ),
                tint = iconTint,
                contentDescription = null
            )
        }
        Spacer(modifier = Modifier.width(10.dp))
        Image(
            modifier = Modifier
                .size(82.dp, 76.dp)
                .clip(RoundedCornerShape(4.dp)),
            painter = rememberAsyncImagePainter(shopItem.imgOfProduct),
            contentDescription = null,
            contentScale = ContentScale.Crop,
        )
        Spacer(modifier = Modifier.width(10.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .size(0.dp, 76.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column()
            {
                Text(
                    modifier = Modifier.padding(top = 5.dp, bottom = 5.dp),
                    text = shopItem.nameOfProduct,
                    style = TextStyle(
                        fontFamily = inter,
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp,
                        lineHeight = 19.36.sp,
                        color = Color(0xFF393F42)
                    )
                )
                Text(
                    text = "Available: ${Random.nextInt(70, 501)} pcs",
                    style = TextStyle(
                        fontFamily = inter,
                        fontWeight = FontWeight.Normal,
                        fontSize = 12.sp,
                        lineHeight = 19.36.sp,
                        color = Color(0xFF939393)
                    )
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .size(0.dp, 24.dp),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier.padding(bottom = 5.dp),
                    text = formattedPrice,
                    style = TextStyle(
                        fontFamily = inter,
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp,
                        lineHeight = 21.78.sp,
                        color = Color(0xFF393F42)
                    )
                )
                QuantityOfProduct(shopItem, onDeleteItem, onQuantityChange)
            }
        }
    }
}

@Composable
fun QuantityOfProduct(
    shopItem: ShopItem,
    onDeleteItem: (ShopItem) -> Unit,
    onQuantityChange: (Int) -> Unit
) {
    var quantityOfProduct by remember { mutableStateOf(shopItem.quantity) }
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            modifier = Modifier
                .size(24.dp),
            onClick = {
                if (quantityOfProduct > 1) {
                    quantityOfProduct--
                    onQuantityChange(quantityOfProduct)
                }
            }) {
            Icon(
                modifier = Modifier
                    .size(24.dp),
                imageVector = ImageVector.vectorResource(R.drawable.decrease_icon),
                tint = if (quantityOfProduct == 1) Color(0xFFD9D9D9)
                else Color.Unspecified,
                contentDescription = null
            )
        }
        Text(
            modifier = Modifier
                .padding(start = 6.dp, end = 6.dp),
            text = quantityOfProduct.toString(),
            style = TextStyle(
                fontFamily = inter,
                fontWeight = FontWeight(400),
                fontSize = 14.sp,
                lineHeight = 16.94.sp,
                color = Color(0xFF393F42),
                textAlign = TextAlign.Center
            )
        )
        IconButton(
            modifier = Modifier
                .size(24.dp),
            onClick = {
                quantityOfProduct++
                onQuantityChange(quantityOfProduct)
            }) {
            Icon(
                modifier = Modifier
                    .size(24.dp),
                imageVector = ImageVector.vectorResource(R.drawable.increase_icon),
                tint = Color.Unspecified,
                contentDescription = null
            )
        }
        Spacer(modifier = Modifier.width(6.dp))
        IconButton(
            modifier = Modifier
                .size(24.dp),
            onClick = {
                onDeleteItem(shopItem)
                onQuantityChange(0)
            }) {
            Icon(
                modifier = Modifier
                    .size(24.dp),
                imageVector = ImageVector.vectorResource(R.drawable.trash_can_icon),
                tint = Color.Unspecified,
                contentDescription = null
            )
        }
    }
}

@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderSummary(
    totalPrice: Double,
    isAtLeastOneItemSelected: Boolean,
    viewModel: StoreViewModel,
    selectedItems: List<ShopItem>
) {

    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }
    var currency = viewModel.currentCurrency.value

    Column {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .size(0.dp, 81.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Order Summary",
                style = TextStyle(
                    fontFamily = inter,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
                    lineHeight = 21.78.sp,
                    color = Color(0xFF393F42)
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Totals",
                    style = TextStyle(
                        fontFamily = inter,
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp,
                        lineHeight = 21.78.sp,
                        color = Color(0xFF393F42)
                    )
                )
                Text(
                    text = viewModel.formatPriceWithCurrency(totalPrice, currency),
                    style = TextStyle(
                        fontFamily = inter,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp,
                        lineHeight = 21.78.sp,
                        color = Color(0xFF393F42)
                    )
                )
            }
        }
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .size(0.dp, 45.dp),
            shape = RoundedCornerShape(4.dp),
            colors = ButtonDefaults
                .buttonColors(
                    containerColor = (
                            if (isAtLeastOneItemSelected) Color(0xFF67C4A7)
                            else Color(0xFFF0F2F1))
                ),
            onClick = {
                if (isAtLeastOneItemSelected) {
                    showBottomSheet = true
                    selectedItems.forEach {
                        viewModel.removeFromCart(it.productId)
                    }
                }
            }
        )
        {
            Text(
                text = "Selected payment method",
                style = TextStyle(
                    fontFamily = inter,
                    fontWeight = FontWeight(500),
                    fontSize = 14.sp,
                    lineHeight = 16.94.sp,
                    color = Color.White
                )
            )
        }
        Spacer(modifier = Modifier.height(43.dp))
    }
    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                showBottomSheet = false
            },
            sheetState = sheetState,

            modifier = Modifier
                .fillMaxHeight(0.5f)
                .navigationBarsPadding(),
            containerColor = Color.White,
            dragHandle = null

        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 20.dp, end = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 28.dp, bottom = 28.dp),
                    horizontalArrangement = Arrangement.End

                ) {
                    IconButton(
                        modifier = Modifier
                            .size(14.dp),
                        onClick = {
                            scope.launch { sheetState.hide() }.invokeOnCompletion {
                                if (!sheetState.isVisible) {
                                    showBottomSheet = false
                                }
                            }
                        }
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.close_icon),
                            tint = Color.Black,
                            contentDescription = null
                        )
                    }
                }
                Image(
                    modifier = Modifier
                        .weight(1f),
                    painter = painterResource(id = R.drawable.payment_success),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                )
                Spacer(modifier = Modifier.height(37.dp))
                Column {
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .size(0.dp, 45.dp),
                        shape = RoundedCornerShape(4.dp),
                        colors = ButtonDefaults
                            .buttonColors(
                                Color(0xFF67C4A7)

                            ),
                        onClick = {
                            scope.launch { sheetState.hide() }.invokeOnCompletion {
                                if (!sheetState.isVisible) {
                                    showBottomSheet = false
                                }
                            }
                        })
                    {
                        Text(
                            text = "Continue",
                            style = TextStyle(
                                fontFamily = inter,
                                fontWeight = FontWeight(500),
                                fontSize = 14.sp,
                                lineHeight = 16.94.sp,
                                color = Color.White
                            )
                        )
                    }
                    Spacer(modifier = Modifier.height(43.dp))
                }
            }
        }
    }
}

fun parsePrice(priceStr: String): Double {
    return priceStr.replace("[^\\d.]".toRegex(), "").replace(',', '.').toDoubleOrNull() ?: 0.0
}