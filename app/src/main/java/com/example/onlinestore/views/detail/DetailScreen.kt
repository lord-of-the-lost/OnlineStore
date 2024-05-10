package com.example.onlinestore.views.detail

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.Text
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.onlinestore.R
import com.example.onlinestore.core.StoreViewModel
import com.example.onlinestore.core.models.ProductModel
import com.example.onlinestore.ui.theme.inter
import kotlinx.coroutines.launch

@Composable
fun DetailScreen(viewModel: StoreViewModel) {
    val product by viewModel.selectedProduct.collectAsState()
    val currentCurrency by viewModel.currentCurrency.collectAsState()
    val priceOfProduct =
        product?.price?.let {
            viewModel.formatPriceWithCurrency(
                it.toDouble(),
                currentCurrency
            )
        }
            ?: "0"
    val descriptionOfProductContent = product?.description ?: "Empty description"



    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        product?.let {
            ImgOfProduct(it)
            NameWithPriceOfProduct(it, viewModel, priceOfProduct)
        }
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
        Buttons(viewModel, product)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImgOfProduct(product: ProductModel) {
    if (product.images.isEmpty()) return

    val pagerState = rememberPagerState(pageCount = { product.images.size })

    Box {
        HorizontalPager(
            modifier = Modifier
                .fillMaxWidth()
                .height(296.dp),
            state = pagerState
        ) { page ->
            val image = if (product.images[page].startsWith("[")) {
                product.images[page].substring(2, product.images[page].length - 2)
            } else {
                product.images[page]
            }

            AsyncImage(
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.Crop,
                model = image,
                contentDescription = "Photo",
                error = painterResource(R.drawable.maxresdefault)
            )
        }

        Row(
            Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(pagerState.pageCount) { index ->
                val color =
                    if (pagerState.currentPage == index) Color.DarkGray else Color(0x55CCCCCC)
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
    product: ProductModel,
    viewModel: StoreViewModel,
    priceOfProduct: String?
) {
    val favoriteProducts by viewModel.favoriteProducts.collectAsState()
    val isFavorite = favoriteProducts.contains(product.id)


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, top = 9.dp, end = 20.dp)
            .size(0.dp, 47.dp),
    ) {
        Row() {
            Text(
                text = product.title,
                style = TextStyle(
                    fontFamily = inter,
                    fontWeight = FontWeight(500),
                    fontSize = 16.sp,
                    lineHeight = 19.36.sp,
                    color = Color(0xFF393F42)
                ),
                modifier = Modifier.weight(1f)
            )
            IconButton(
                modifier = Modifier
                    .size(46.dp),
                onClick = { viewModel.toggleFavorite(product.id) }) {
                Icon(
                    imageVector = ImageVector.vectorResource(
                        if (isFavorite) R.drawable.heart_fill
                        else
                            R.drawable.heart
                    ),
                    tint = Color(0xFF939393),
                    contentDescription = null
                )
            }
        }
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
fun Buttons(viewModel: StoreViewModel, product: ProductModel?) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 14.dp, bottom = 43.dp),
        Arrangement.Center
    )
    {
        AddToCardButton(viewModel, product)
        Spacer(modifier = Modifier.width(15.dp))
        BuyNowButton()
    }
}

@Composable
fun AddToCardButton(viewModel: StoreViewModel, product: ProductModel?) {

    Button(
        modifier = Modifier
            .size(167.dp, 45.dp),
        shape = RoundedCornerShape(4.dp),
        colors = ButtonDefaults
            .buttonColors(
                containerColor = Color(0xFF67C4A7)
            ),
        onClick = { product?.let { viewModel.addToCart(it) } })
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

@Preview(showBackground = true)
@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun BuyNowButton() {

    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }

    Button(
        modifier = Modifier
            .size(167.dp, 45.dp),
        shape = RoundedCornerShape(4.dp),
        colors = ButtonDefaults
            .buttonColors(
                containerColor = Color(0xFFF0F2F1)
            ),
        border = BorderStroke(1.dp, Color(0xFFD9D9D9)),
        onClick = { showBottomSheet = true }
    ) {
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
    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                showBottomSheet = false
            },
            sheetState = sheetState,
            containerColor = Color.White,

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
                    androidx.compose.material.IconButton(
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
                    modifier = Modifier,
                    painter = painterResource(id = R.drawable.payment_success),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                )


                Spacer(modifier = Modifier.height(300.dp))
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