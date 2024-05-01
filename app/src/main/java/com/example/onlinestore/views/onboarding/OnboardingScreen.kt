package com.example.onlinestore.views.onboarding


import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.onlinestore.R
import com.example.onlinestore.navigation.Screen
import com.example.onlinestore.ui.theme.inter
import com.example.onlinestore.ui.theme.plusJakartaSans
import kotlinx.coroutines.launch

@SuppressLint("RememberReturnType")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingScreen(modifier: Modifier, navController: NavController) {


    val listOfImages = listOf(
        R.drawable._01_onboarding,
        R.drawable._02_onboarding,
        R.drawable._03_onboarding
    )

    val pagerState = rememberPagerState(pageCount = { listOfImages.size })
    val pagerScope = rememberCoroutineScope()

    var headTextIndex by remember { mutableIntStateOf(0) }
    val headTexts = listOf(
        "20% Discount" +
                "\nNew Arrival Product",
        "Take Advantage" +
                "\nOf The Offer Shopping",
        "All Types Offers" +
                "\nWithin You Reach"
    )

    var descriptionHeadTextIndex by remember { mutableIntStateOf(0) }
    val descriptionHeadTexts = listOf(
        "Publish up your selfies to make yourself more beautiful with this app.",
        "Improve your beauty with the help of our products!" +
                "\n ",
        "Make your life easier with our new products!" +
                "\n "
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(14.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalPager(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(24.dp)),
            state = pagerState
        ) { index ->

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(345.dp, 447.dp)
                        .clip(RoundedCornerShape(24.dp)),
                    painter = painterResource(id = listOfImages[index]),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
            }
            if (pagerState.currentPage == index) {
                headTextIndex = index
                descriptionHeadTextIndex = index
            }
        }
        Spacer(modifier = Modifier.height(66.dp))

        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = headTexts[headTextIndex],
            style = TextStyle(
                fontFamily = plusJakartaSans,
                fontSize = 29.sp,
                lineHeight = 45.sp,
                color = Color.Black,
            )
        )

        Spacer(modifier = Modifier.height(28.dp))

        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = descriptionHeadTexts[descriptionHeadTextIndex],
            style = TextStyle(
                fontFamily = inter,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                lineHeight = 25.sp,
                color = Color(0xFF7C7C7B),
            )
        )
        Spacer(modifier = Modifier.height(50.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween

        ) {
            Box()
            {
                Row(
                    Modifier
                        .wrapContentHeight(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    repeat(pagerState.pageCount) { iteration ->
                        val color =
                            if (pagerState.currentPage == iteration) Color.Black else Color(
                                0xFFD9D9D9
                            )
                        val x =
                            if (pagerState.currentPage == iteration) 24.dp else 8.dp
                        Spacer(modifier = Modifier.width(4.dp))
                        Box(
                            modifier = Modifier
                                .clip(CircleShape)
                                .background(color)
                                .size(x, 8.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                    }
                }
            }

            IconButton(
                modifier = Modifier
                    .size(64.dp),
                onClick = {
                    pagerScope.launch { pagerState.scrollToPage(pagerState.currentPage + 1) }
                    if (descriptionHeadTextIndex == 2) {
                        navController.navigate(Screen.NavigationItem.Registration.route)
                    }
                }) {
                Icon(
                    modifier = Modifier
                        .size(64.dp),
                    imageVector = ImageVector.vectorResource(R.drawable.onboarding_button),
                    tint = Color.Black,
                    contentDescription = null
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun OnboardingScreenPreview() {
    OnboardingScreen(
        Modifier
            .fillMaxSize()
            .background(Color.White),
        rememberNavController()
    )
}