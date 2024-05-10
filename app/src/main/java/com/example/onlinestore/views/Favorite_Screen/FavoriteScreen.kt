package com.example.onlinestore.views.Favorite_Screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.onlinestore.R
import com.example.onlinestore.core.StoreViewModel
import com.example.onlinestore.core.models.ProductModel
import com.example.onlinestore.navigation.Screen

@Composable
fun FavoriteScreen(navController: NavController, viewModel: StoreViewModel) {
    LaunchedEffect(key1 = true) {
        viewModel.getProductsFromDB()
    }
    val productList = viewModel.savedProducts.collectAsState().value

    if (productList.isNullOrEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("No favorite products found")
        }
    } else {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            state = rememberLazyGridState(),
            modifier = Modifier.padding(20.dp)
        ) {
            items(productList.toList()) { product ->
                FavoriteItem(navController, viewModel, product)
            }
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FavoriteItem(
    navController: NavController,
    viewModel: StoreViewModel,
    productItem: ProductModel
) {
    val images = productItem.images.map { img ->
        if (img.startsWith("[")) img.substring(2, img.length - 2)
        else img
    }.filterNot { it.isBlank() }
    var addToCard by rememberSaveable { mutableStateOf(false) }
    val pagerState = rememberPagerState(pageCount = { images.size })
    val currentCurrency by viewModel.currentCurrency.collectAsState()
    val priceOfProduct =
        productItem.price.let { viewModel.formatPriceWithCurrency(it.toDouble(), currentCurrency) }
            ?: "0"

    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(3.dp)
            .size(170.dp, 215.dp),
        colors = CardDefaults.cardColors(colorResource(id = R.color.CardColor)),
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.5f)
                    .clickable {
                        viewModel.setSelectedProduct(productItem)
                        navController.navigate(Screen.NavigationItem.DetailProductScreen.tRoute)
                    }
            ) {
                HorizontalPager(state = pagerState) { page ->
                    if (images.isNotEmpty()) {
                        AsyncImage(
                            model = images[page], contentDescription = "photo",
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Image(
                            painter = painterResource(id = R.drawable.maxresdefault),
                            contentDescription = "Default image"
                        )
                    }
                }
            }
            Box(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(text = productItem.title, fontSize = 12.sp, maxLines = 1)
                    Text(
                        text = priceOfProduct,
                        color = colorResource(id = R.color.Dark_Arsenic),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                        IconButton({ viewModel.toggleFavorite(productItem.id) }) {
                            Icon(
                                painterResource(R.drawable.ic_wishlist),
                                contentDescription = "Favorite",
                                tint = colorResource(R.color.Green_Sheen)
                            )
                        }
                        Button(
                            onClick = {
                                addToCard = !addToCard
                                if (addToCard) viewModel.addToCart(productItem) else viewModel.removeFromCart(
                                    productItem.id
                                )
                            },
                            Modifier
                                .fillMaxWidth()
                                .height(29.dp),
                            shape = RoundedCornerShape(5.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = colorResource(if (!addToCard) R.color.Green_Sheen else R.color.Red)),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Text(
                                text = if (!addToCard) "Add to cart" else "Remove",
                                fontSize = 12.sp,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
            }
        }
    }
}