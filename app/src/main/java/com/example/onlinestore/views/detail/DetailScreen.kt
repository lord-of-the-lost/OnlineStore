package com.example.onlinestore.views.detail

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.example.onlinestore.R
import com.example.onlinestore.ui.theme.inter

@Composable
fun DetailScreen(modifier: Modifier, navController: NavController) {

    val nameOfProduct = "Air pods max by Apple"
    val priceOfProduct = "$ 1999,99"
    val descriptionOfProductContent =
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aliquet arcu id tincidunt tellus arcu rhoncus, turpis nisl sed. Neque viverra ipsum orci, morbi semper. Nulla bibendum purus tempor semper purus. Ut curabitur platea sed blandit. Amet non at proin justo nulla et. A, blandit morbi suspendisse vel malesuada purus massa mi. Faucibus neque a mi hendrerit.\n" +
                "\n" +
                "Audio Technology\n" +
                "Apple-designed dynamic driver\n" +
                "Active Noise Cancellation\n" +
                "Transparency mode\n" +
                "Adaptive EQ\n" +
                "Spatial audio with dynamic head tracking1\n" +
                "\n" +
                "Sensors\n" +
                "Optical sensor (each ear cup)\n" +
                "Position sensor (each ear cup)\n" +
                "Case-detect sensor (each ear cup)\n" +
                "Accelerometer (each ear cup)\n" +
                "Gyroscope (left ear cup)\n" +
                "\n" +
                "Microphones\n" +
                "Nine microphones total:\n" +
                "Eight microphones for Active Noise Cancellation\n" +
                "Three microphones for voice pickup (two shared with Active Noise Cancellation and one additional microphone)\n" +
                "\n" +
                "Chip\n" +
                "Apple H1 headphone chip (each ear cup)\n" +
                "\n" +
                "Controls\n" +
                "Digital Crown\n" +
                "Turn for volume control\n" +
                "Press once to play, pause or answer a phone call\n" +
                "Press twice to skip forwards\n" +
                "Press three times to skip back\n" +
                "Press and hold for Siri\n" +
                "Noise control button\n" +
                "Press to switch between Active Noise Cancellation and Transparency mode\n" +
                "\n" +
                "Size and Weight2\n" +
                "AirPods Max, including cushions\n" +
                "Weight: 384.8g\n" +
                "\n" +
                "Smart Case\n" +
                "Weight: 134.5g\n" +
                "\n" +
                "Battery\n" +
                "AirPods Max\n" +
                "Up to 20 hours of listening time on a single charge with Active Noise Cancellation or Transparency mode enabled3\n" +
                "Up to 20 hours of movie playback on a single charge with spatial audio on4\n" +
                "Up to 20 hours of talk time on a single charge5\n" +
                "5 minutes of charge time provides around 1.5 hours of listening time6\n" +
                "AirPods Max with Smart Case\n" +
                "\n" +
                "Storage in the Smart Case preserves battery charge in ultra-low-power state\n" +
                "Charging via Lightning connector\n" +
                "\n" +
                "Connectivity\n" +
                "Bluetooth 5.0\n" +
                "\n" +
                "In the Box\n" +
                "AirPods Max\n" +
                "Smart Case\n" +
                "Lightning to USB-C Cable\n" +
                "Documentation\n" +
                "Accessibility7\n" +
                "Accessibility features help people with disabilities get the most out of their new AirPods Max.\n" +
                "\n" +
                "Features include:\n" +
                "Live Listen audio\n" +
                "Headphone levels\n" +
                "Headphone Accommodations\n" +
                "\n" +
                "System Requirements8\n" +
                "iPhone and iPod touch models with the latest version of iOS\n" +
                "iPad models with the latest version of iPadOS\n" +
                "Apple Watch models with the latest version of watchOS\n" +
                "Mac models with the latest version of macOS\n" +
                "Apple TV models with the latest version of tvOS"

    val productIsBookmarked = false

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ImgOfProduct()
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

@Composable
fun ImgOfProduct() {
    Image(
        modifier = Modifier
            .fillMaxWidth()
            .size(0.dp, 286.dp),
        painter = rememberImagePainter(R.drawable.img_detail_screen),
        contentDescription = null,
        contentScale = ContentScale.Crop,
    )
}

@Composable
fun NameWithPriceOfProduct(
    nameOfProduct: String,
    priceOfProduct: String,
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
                text = nameOfProduct,
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
                text = priceOfProduct,
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
    descriptionOfProductContent: String
) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 9.dp)
            .verticalScroll(rememberScrollState()),
        text = descriptionOfProductContent,
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

@Preview(showBackground = false)
@Composable
fun DetailScreenPreview() {
    DetailScreen(
        Modifier
            .fillMaxSize()
            .background(Color.White),
        rememberNavController()
    )
}

