package com.example.onlinestore.views.detail

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.onlinestore.R
import com.example.onlinestore.core.StoreViewModel
import com.example.onlinestore.ui.theme.inter
import com.example.onlinestore.views.HomeScreen.network.model.ProductItem

@Composable
fun DetailScreen(product: ProductItem, viewModel: StoreViewModel) {
    val currentCurrency by viewModel.currentCurrency.collectAsState()
    val priceOfProduct =
        product.price?.let { viewModel.formatPriceWithCurrency(it.toDouble(), currentCurrency) } ?: "0"
    val nameOfProduct = product.title
    val descriptionOfProductContent = product.description
    val productIsBookmarked = false

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ImgOfProduct(product)
        NameWithPriceOfProduct(nameOfProduct, priceOfProduct, productIsBookmarked)
        DescriptionOfProductHead()
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 20.dp)
        ) {
            DescriptionOfProductContent(descriptionOfProductContent)
        }
        Box(
            modifier = Modifier
                .border(1.dp, Color(0xFFD9D9D9))
                .fillMaxWidth()
                .size(0.dp, 1.dp)
        )
        Buttons()
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImgOfProduct(product: ProductItem) {
    val pagerState = rememberPagerState(pageCount = { product.images.size })
    Box {
        HorizontalPager(
            modifier = Modifier
                .fillMaxWidth()
                .size(0.dp, 296.dp),
            state = pagerState
        ) {
            var image = product.images
            if (product.images[0].startsWith("[")) {
                image = listOf(
                    product.images[0].substring(
                        2,
                        product.images[0].length - 2
                    )
                )
            }
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop,
                model = image[it], contentDescription = "Photo"
            )
        }

        Row(
            Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .offset(y = 280.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(pagerState.pageCount) { iteration ->
                val color =
                    if (pagerState.currentPage == iteration) Color.DarkGray else Color(0x55CCCCCC)
                Box(
                    modifier = Modifier
                        .padding(2.dp)
                        .clip(CircleShape)
                        .background(color)
                        .size(8.dp)
                )
            }
        }
    }
}

@Composable
fun NameWithPriceOfProduct(
    nameOfProduct: String?,
    priceOfProduct: String?,
    productIsBookmarked: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, top = 9.dp, end = 20.dp)
            .size(0.dp, 47.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                text = nameOfProduct.toString(),
                style = TextStyle(
                    fontFamily = inter,
                    fontWeight = FontWeight(500),
                    fontSize = 16.sp,
                    lineHeight = 19.36.sp,
                    color = Color(0xFF393F42)
                )
            )
            Text(
                modifier = Modifier
                    .padding(top = 5.dp),
                text = priceOfProduct.toString(),
                style = TextStyle(
                    fontFamily = inter,
                    fontWeight = FontWeight(500),
                    fontSize = 18.sp,
                    lineHeight = 21.78.sp,
                    color = Color(0xFF393F42)
                )
            )
        }
        IconButton(
            modifier = Modifier
                .size(46.dp),
            onClick = { }) {
            Icon(
                imageVector = ImageVector.vectorResource(
                    if (productIsBookmarked) R.drawable.heart_fill
                    else
                        R.drawable.heart
                ),
                tint = Color(0xFF939393),
                contentDescription = null
            )
        }
    }
}

@Composable
fun DescriptionOfProductHead(
) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, top = 12.dp, end = 20.dp),
        text = "Description of product",
        style = TextStyle(
            fontFamily = inter,
            fontWeight = FontWeight(500),
            fontSize = 16.sp,
            lineHeight = 19.36.sp,
            color = Color(0xFF393F42)
        )
    )
}

@Composable
fun DescriptionOfProductContent(
    descriptionOfProductContent: String?
) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 9.dp)
            .verticalScroll(rememberScrollState()),
        text = descriptionOfProductContent.toString(),
        style = TextStyle(
            fontFamily = inter,
            fontWeight = FontWeight(400),
            fontSize = 12.sp,
            lineHeight = 21.sp,
            color = Color(0xFF393F42)
        )
    )
}

@Composable
fun Buttons() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 14.dp, bottom = 43.dp),
        Arrangement.Center
    )
    {
        AddToCardButton()
        Spacer(modifier = Modifier.width(15.dp))
        BuyNowButton()
    }
}

@Composable
fun AddToCardButton() {
    Button(
        modifier = Modifier
            .size(167.dp, 45.dp),
        shape = RoundedCornerShape(4.dp),
        colors = ButtonDefaults
            .buttonColors(
                containerColor = Color(0xFF67C4A7)
            ),
        onClick = { })
    {
        Text(
            text = "Add to Cart",
            style = TextStyle(
                fontFamily = inter,
                fontWeight = FontWeight(500),
                fontSize = 14.sp,
                lineHeight = 16.94.sp,
                color = Color.White
            )
        )
    }
}

@Composable
fun BuyNowButton() {
    Button(
        modifier = Modifier
            .size(167.dp, 45.dp),
        shape = RoundedCornerShape(4.dp),
        colors = ButtonDefaults
            .buttonColors(
                containerColor = Color(0xFFF0F2F1)
            ),
        border = BorderStroke(1.dp, Color(0xFFD9D9D9)),
        onClick = { })
    {
        Text(
            text = "Buy Now",
            style = TextStyle(
                fontFamily = inter,
                fontWeight = FontWeight(500),
                fontSize = 14.sp,
                lineHeight = 16.94.sp,
                color = Color(0xFF393F42)
            )
        )
    }
}