package com.example.onlinestore.views.terms_conditions

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.onlinestore.R
import com.example.onlinestore.navigation.Screen


@Composable
fun Terms() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .verticalScroll(rememberScrollState())

    ) {
        Text(text = "Terms&Conditions",
        modifier = Modifier.padding(top = 16.dp, bottom = 16.dp),
        color = Color.Black,
        style = TextStyle(
            fontSize = 24.sp,
            textAlign = TextAlign.Center
        )
        )
        Text( stringResource(R.string.Terms),
            modifier = Modifier.padding(start = 10.dp, end = 10.dp),
            color = Color.Black
        )
    }
}