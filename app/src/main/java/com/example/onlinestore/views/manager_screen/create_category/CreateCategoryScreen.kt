package com.example.onlinestore.views.manager_screen.create_category

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.example.onlinestore.R
import com.example.onlinestore.core.StoreViewModel
import com.example.onlinestore.core.models.RequestModel.PostCategoryModel
import com.example.onlinestore.views.manager_screen.add_screen.Element

@Composable
fun CreateCategory(viewModel: StoreViewModel) {
    var name by remember { mutableStateOf("") }
    var image by remember { mutableStateOf("") }
    var isActive by remember { mutableStateOf(false) }
    val category by remember { mutableStateOf(PostCategoryModel("", "")) }
    val context = LocalContext.current


    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column() {
            Element(value = name, onValueChanged = { name = it }, text = "Name", textSize = 17)
            Spacer(modifier = Modifier.height(32.dp))
            Element(value = image, onValueChanged = { image = it }, text = "Image", textSize = 17)

            if (name != "" && image != "") {
                isActive = true
                category.name = name
                category.image = image
            }
            Spacer(modifier = Modifier.height(32.dp))
            Button(
                modifier = Modifier
                    .height(56.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.Green_Sheen)
                ),
                onClick = {
                    viewModel.postNewCategory(category,context)
                    name = ""
                    image = ""
                },
                enabled = isActive
            ) {
                Text("Create category")
            }
        }
    }
}