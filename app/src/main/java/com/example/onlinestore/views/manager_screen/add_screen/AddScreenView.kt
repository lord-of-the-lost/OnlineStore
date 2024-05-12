package com.example.onlinestore.views.manager_screen.add_screen

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.onlinestore.R
import com.example.onlinestore.core.StoreViewModel
import com.example.onlinestore.core.models.PostProductModel

@Composable
fun AddProduct(
    viewModel: StoreViewModel
) {
    val categoryList by viewModel.categories.collectAsState()
    val categoryId by viewModel.categoryId.collectAsState()


    var title by remember {
        mutableStateOf("")
    }
    var price by remember {
        mutableStateOf("")
    }
    var description by remember {
        mutableStateOf("")
    }
    var images by remember {
        mutableStateOf("")
    }
    Column(
        Modifier
            .fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(32.dp))

        Element(text = "Title", value = title, onValueChanged = { title = it }, 17)

        Spacer(modifier = Modifier.height(32.dp))

        Element(text = "Price", value = price, onValueChanged = { price = it }, 17)

        Spacer(modifier = Modifier.height(8.dp))

        ElementDropDown(categoryList, viewModel)

        Spacer(modifier = Modifier.height(8.dp))

        Element(
            text = "Description",
            value = description,
            onValueChanged = { description = it },
            17
        )

        Element(text = "Images", value = images, onValueChanged = { images = it }, 16)


        if (price != "" && title != "" && description != "" && images != "") {
            Spacer(modifier = Modifier.padding(top = 40.dp))
            PostButton(title, price.toInt(), description, categoryId, images, viewModel)

        }

    }


}

@Composable
fun PostButton(
    title: String,
    price: Int,
    description: String,
    categoryId: Int,
    images:String,
    viewModel: StoreViewModel
) {
    val context = LocalContext.current
    val image: List<String> by remember { mutableStateOf( images.split(" "))}
    val product by remember {
        mutableStateOf(
            PostProductModel(
                title,
                price,
                description,
                categoryId,
                image
            )
        )
    }

    Button(
        onClick =
        {
            viewModel.postNewProduct(product)
            Toast.makeText(context,"Success Added",Toast.LENGTH_LONG).show()
        },
        modifier = Modifier
            .height(56.dp)
            .fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(R.color.Green_Sheen),
        )
    ) {
        Text("Send", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
    }
}