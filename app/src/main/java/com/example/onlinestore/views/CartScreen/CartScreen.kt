package com.example.onlinestore.views.CartScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.example.onlinestore.ui.theme.inter
import kotlin.random.Random


data class ShopItem(
    val imgOfProduct: Int,
    val nameOfProduct: String,
    val priceOfProduct: String,
    val quantity: Int,
    var isSelected: Boolean = false
)

var shopItemsInCart = mutableListOf(
    ShopItem(
        R.drawable.img_detail_screen,
        "Air pods max by Apple",
        "\$ 1999.99",
        1,
        false
    ),
    ShopItem(
        R.drawable._01_cart,
        "Monitor LG 22”inc 4K 120Fps",
        "\$ 199,99",
        1,
        false
    ),
    ShopItem(
        R.drawable._02_cart,
        "Earphones for monitor",
        "\$ 199,99",
        1,
        false
    ),
    ShopItem(
        R.drawable.img_detail_screen,
        "Air pods max by Apple",
        "\$ 1999.99",
        1,
        false
    ),
    ShopItem(
        R.drawable._01_cart,
        "Monitor LG 22”inc 4K 120Fps",
        "\$ 199,99",
        1,
        false
    ),
    ShopItem(
        R.drawable._02_cart,
        "Earphones for monitor",
        "\$ 199,99",
        1,
        false
    ),
    ShopItem(
        R.drawable.img_detail_screen,
        "Air pods max by Apple",
        "\$ 1999.99",
        1,
        false
    ),
    ShopItem(
        R.drawable._01_cart,
        "Monitor LG 22”inc 4K 120Fps",
        "\$ 199,99",
        1,
        false
    ),
    ShopItem(
        R.drawable._02_cart,
        "Earphones for monitor",
        "\$ 199,99",
        1,
        false
    ),
    ShopItem(
        R.drawable.img_detail_screen,
        "Air pods max by Apple",
        "\$ 1999.99",
        1,
        false
    ),
    ShopItem(
        R.drawable._01_cart,
        "Monitor LG 22”inc 4K 120Fps",
        "\$ 199,99",
        1,
        false
    ),
    ShopItem(
        R.drawable._02_cart,
        "Earphones for monitor",
        "\$ 199,99",
        1,
        false
    ),
)

@SuppressLint("MutableCollectionMutableState")
@Composable
fun CartScreen(
) {

    var shopItemsInCart by remember { mutableStateOf(shopItemsInCart) }
    var isAtLeastOneItemSelected by remember { mutableStateOf(false) }

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
                    onDeleteItem = {
                        shopItemsInCart = shopItemsInCart.toMutableList()
                            .also { it.remove(shopItem) }
                    },
                    onItemSelectedChanged = {
                        isAtLeastOneItemSelected = shopItemsInCart.any { it.isSelected }
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
            OrderSummary(isAtLeastOneItemSelected)
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
    onDeleteItem: (ShopItem) -> Unit,
    onItemSelectedChanged: (Boolean) -> Unit
) {
    var isSelected by remember { mutableStateOf(false) }
    isSelected = shopItem.isSelected

    if (isSelected) {
        onItemSelectedChanged(true)
    } else onItemSelectedChanged(false)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 22.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(modifier = Modifier
            .size(24.dp),
            onClick = {
                if (!isSelected) {
                    isSelected = true
                    shopItem.isSelected = isSelected
                } else {
                    isSelected = false
                    shopItem.isSelected = isSelected
                }
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
                    text = shopItem.priceOfProduct,
                    style = TextStyle(
                        fontFamily = inter,
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp,
                        lineHeight = 21.78.sp,
                        color = Color(0xFF393F42)
                    )
                )
                QuantityOfProduct(shopItem, onDeleteItem)
            }
        }
    }
}

@Composable
fun QuantityOfProduct(
    shopItem: ShopItem,
    onDeleteItem: (ShopItem) -> Unit
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
            onClick = { onDeleteItem(shopItem) }) {
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

@Composable
fun OrderSummary(isAtLeastOneItemSelected: Boolean) {
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
                    text = "\$ 2499,97",
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
            onClick = { }
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
}

@Preview(showBackground = true)
@Composable
fun ShoppingListItemPreview() {
    CartScreen()
}