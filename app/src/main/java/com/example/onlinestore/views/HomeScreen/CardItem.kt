package com.example.onlinestore.views.HomeScreen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.onlinestore.R
import com.example.onlinestore.views.HomeScreen.networkTest.ProductItem

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CardItem(productItem: ProductItem) {
    val pagerState = rememberPagerState(pageCount = { productItem.images.size })
    val pagerScope = rememberCoroutineScope()
    Card(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {
            Box(modifier = Modifier.fillMaxHeight(0.5f)) {
                HorizontalPager(state = pagerState) {
                    AsyncImage(model = productItem.images[it], contentDescription = "photo")
                }
            }
            Text(text = productItem.title.toString())
            Text(text = productItem.price.toString())
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

@Preview(showBackground = true)
@Composable
fun CardItemPreview() {
    CardItem(productItem = ProductItem(1, "Monitor", 1988, "qweqw", emptyList(), "q"))
}
