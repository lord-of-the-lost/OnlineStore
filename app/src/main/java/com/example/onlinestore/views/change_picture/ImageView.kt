package com.example.onlinestore.views.change_picture

import android.graphics.Bitmap
import androidx.compose.foundation.Image


import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.onlinestore.R

@Composable
fun ImageViewForProfile(
    bitmap: Bitmap?
) {
    Box {
        if (bitmap != null) {
            Image(
                bitmap = bitmap.asImageBitmap(),
                contentDescription = "profile picture",
                Modifier
                    .width(140.dp)
                    .height(120.dp)
                    .fillMaxSize()
                    .clip(RoundedCornerShape(100))
            )
        }else{
            Image(
                painter = painterResource(id = R.drawable.avatar),
                contentDescription = "profile picture",
                Modifier
                    .width(140.dp)
                    .height(120.dp)
                    .fillMaxSize()
                    .clip(RoundedCornerShape(100))
            )
        }

    }
}

