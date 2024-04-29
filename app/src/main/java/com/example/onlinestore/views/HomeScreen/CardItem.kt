package com.example.onlinestore.views.HomeScreen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.onlinestore.R
import com.example.onlinestore.navigation.Screen
import com.example.onlinestore.views.HomeScreen.networkTest.ProductItem

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CardItem(
    productItem: ProductItem,
    navigateToDetail: (ProductItem) -> Unit,) {
    val pagerState = rememberPagerState(pageCount = { productItem.images.size })

    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(3.dp)
            .size(170.dp, 217.dp),
        colors = CardDefaults.cardColors(colorResource(id = R.color.CardColor)),
    ) {
        Column(
            modifier = Modifier.fillMaxSize()

        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.5f)
                    .clickable {
                        navigateToDetail(productItem)
                    }
            ) {
                var image = productItem.images
                HorizontalPager(state = pagerState) {
                    if (productItem.images[0].startsWith("[")) {
                        image = listOf(
                            productItem.images[0].substring(
                                2,
                                productItem.images[0].length - 2
                            )
                        )
                    }
                    if (image[0].contains("https://placeimg.com/640/480/any")) {
                        Image(painter = painterResource(id = R.drawable.maxresdefault), "")
                    } else {
                        AsyncImage(
                            model = image[it], contentDescription = "photo",
                            contentScale = ContentScale.Crop
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
                    Text(text = productItem.title.toString(), fontSize = 12.sp, maxLines = 1)
                    Text(
                        text = "$${productItem.price.toString()}",
                        color = colorResource(id = R.color.Dark_Arsenic),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Button(
                        onClick = { /*TODO*/ },
                        Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(5.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.Green_Sheen))
                    ) {
                        Text(text = "Add to cart")

                    }
                }
            }


        }
    }
}