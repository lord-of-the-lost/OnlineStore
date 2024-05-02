package com.example.onlinestore.views.profile_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.onlinestore.R
import com.example.onlinestore.ui.theme.CustomGrey
import com.example.onlinestore.ui.theme.CustomGrey2

@Composable
@Preview(showBackground = true)
fun ProfileScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Spacer(Modifier.height(14.dp))
        Row (verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .padding(horizontal = 27.dp)
                    .size(102.dp),
                contentAlignment = Alignment.BottomEnd
            ) {
                Image(
                    painter = painterResource(id = R.drawable.avatar),
                    contentDescription = "profile picture",
                    Modifier
                        .width(140.dp)
                        .height(120.dp)
                        .fillMaxSize()
                        .clip(RoundedCornerShape(100))
                )
                Image(
                    painter = painterResource(id = R.drawable.ic_edit),
                    contentDescription = null,
                    Modifier
                        .width(32.dp)
                        .height(32.dp)
                        .fillMaxSize()
                        .clickable {  }
                )
            }
            Box(
                modifier = Modifier
                    .height(69.5.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxHeight(),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "Dev P",
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.W600,
                            lineHeight = 24.sp,
                            color = Color.Black
                        ),
                        textAlign = TextAlign.Start,
                    )
                    Text(
                        text = "dev@gmail.com",
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.W400,
                            lineHeight = 24.sp,
                            color = CustomGrey2,
                            textDecoration = TextDecoration.Underline,
                            textAlign = TextAlign.Start,
                        )
                    )
                    Row {
                        Image(
                            painter = painterResource(id = R.drawable.ic_eye),
                            contentDescription = null,
                            modifier = Modifier.clickable {  }
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = "***********", style = TextStyle(color = CustomGrey2))
                    }
                }
            }
        }
        Spacer(Modifier.weight(1.0f))
        ActionButton(title = "Type of Account", painterResource = R.drawable.ic_arrow, {})
        Spacer(Modifier.height(22.dp))
        ActionButton(title = "Terms & Conditions", painterResource = R.drawable.ic_arrow, {})
        Spacer(Modifier.height(22.dp))
        ActionButton(title = "Sign Out", painterResource = R.drawable.ic_signout, {})
        Spacer(Modifier.height(33.dp))
    }
}


@Composable
fun ActionButton(title: String, painterResource: Int, action: () -> Unit) {
    Button(
        onClick = { action() },
        modifier = Modifier
            .height(56.dp)
            .padding(start = 20.dp, end = 20.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(Color(0xFFF3F4F6))
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = title,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W600,
                    lineHeight = 24.sp,
                    color = Color(0xFF666C8E),
                ),
                textAlign = TextAlign.Start,
            )
            Image(
                painter = painterResource(id = painterResource),
                contentDescription = null,
                modifier = Modifier
                    .width(24.dp)
                    .height(24.dp),
                alignment = Alignment.Center
            )
        }
    }
}

