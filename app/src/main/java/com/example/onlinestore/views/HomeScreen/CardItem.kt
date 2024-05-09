package com.example.onlinestore.views.HomeScreen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.onlinestore.R
import com.example.onlinestore.core.StoreViewModel
import com.example.onlinestore.core.models.ProductModel
import com.example.onlinestore.navigation.Screen

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CardItem(
    productItem: ProductModel,
    viewModel: StoreViewModel,
    navController: NavController
) {
    val images = productItem.images.map { img ->
        if (img.startsWith("[")) img.substring(2, img.length - 2) else img
    }.filter { it.isNotEmpty() }

    val pagerState = rememberPagerState(pageCount = { images.size })
    var addToCard by rememberSaveable { mutableStateOf(false) }

    val currentCurrency by viewModel.currentCurrency.collectAsState()
    val price = productItem.price?.let {
        viewModel.formatPriceWithCurrency(it.toDouble(), currentCurrency)
    } ?: "0"

    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(3.dp)
            .size(170.dp, 217.dp),
        colors = CardDefaults.cardColors(colorResource(id = R.color.CardColor)),
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .clickable {
                        viewModel.setSelectedProduct(productItem)
                        navController.navigate(Screen.NavigationItem.DetailProductScreen.tRoute)
                    }
            ) {
                if (images.isNotEmpty()) {
                    HorizontalPager(state = pagerState) { page ->
                        AsyncImage(
                            model = images[page], contentDescription = "photo",
                            contentScale = ContentScale.Crop,
                            error = painterResource(R.drawable.maxresdefault)
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
                    Text(text = productItem.title.take(20) + "...", fontSize = 12.sp, maxLines = 1)
                    Text(
                        text = price,
                        color = colorResource(id = R.color.Dark_Arsenic),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Button(
                        onClick = {
                            addToCard = !addToCard
                            if (addToCard) viewModel.addToCart(productItem) else viewModel.removeFromCart(
                                productItem.id
                            )
                        },
                        Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(5.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = colorResource(if (!addToCard) R.color.Green_Sheen else R.color.Red))
                    ) {
                        Text(text = if (!addToCard) "Add to cart" else "Remove")
                    }
                }
            }
        }
    }
}