package com.example.onlinestore.views.search_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.onlinestore.R
import com.example.onlinestore.core.StoreViewModel

@Composable
fun HistoryListItem(
    item: String,
    onDeleteClick: () -> Unit,
    viewModel:StoreViewModel
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, top = 8.dp, bottom = 8.dp, end = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.padding(end = 16.dp),
            imageVector = ImageVector.vectorResource(id = R.drawable.time_circle),
            contentDescription = null,
            tint = colorResource(id = R.color.gray_search)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = item,
                fontSize = (19.sp),
                fontWeight = FontWeight(400),
                color = colorResource(id = R.color.gray_search),
                modifier = Modifier.clickable {
                  viewModel.updateSearch(item)
                }
            )
            Icon(
                modifier = Modifier
                    .clickable {
                        onDeleteClick()
                    },
                imageVector = ImageVector.vectorResource(id = R.drawable.close_icon),
                contentDescription = null,
                tint = colorResource(id = R.color.gray_search)
            )
        }
    }
}