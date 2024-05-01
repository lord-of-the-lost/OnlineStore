package com.example.onlinestore.navigation


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.onlinestore.R
import com.example.onlinestore.views.search_screen.HistoryItem

@Composable
fun TopNavigationBar(
    onBackClicked: () -> Unit = {},
    controller: NavController
) {

    val currentRoute = currentRoute(controller)
    val title = allScreen.firstOrNull(){it.route == currentRoute}?.title?:"Unknown"
    val icon = topScreens.firstOrNull { it.route == currentRoute }?.icon
    val action = topScreens.firstOrNull { it.route == currentRoute }?.actionIcon
        ?: bottomScreen.firstOrNull { it.route == currentRoute }?.actionIcon

    val navigationIcon: (@Composable () -> Unit) =
        {
            IconButton(onClick = { onBackClicked() }) {
                icon?.let { Icon(it, "") }
            }

        }
    val actionIcon: (@Composable () -> Unit) = {
        IconButton(onClick = { controller.navigate(Screen.NavigationItem.Cart.route) }) {
            action?.let { painterResource(it) }
                ?.let { Icon(painter = it, "", tint = colorResource(R.color.Dark_Arsenic)) }
        }
    }
    val text = remember {
        mutableStateOf("")
    }


    TopAppBar(
        title = {
            if (title == "Wishlist" || title == "SearchResult") {
                SearchBar(text.value) { text.value = it }
            } else {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        title,
                        color = Color.Black,
                    )
                }
            }

        },
        navigationIcon = if (icon == null) null else navigationIcon,
        elevation = 5.dp,
        backgroundColor = Color.White,
        modifier = Modifier.height(80.dp),
        actions = {if(action !=null) actionIcon()
        }


    )

}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SearchBar(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit
) {
    val interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
    BasicTextField(
        value = searchQuery,
        onValueChange = { onSearchQueryChange(it) },
        modifier = Modifier
            .background(Color.White)
            .fillMaxWidth()
            .height(40.dp)
            .border(1.dp, Color.LightGray, RoundedCornerShape(10.dp)),
        textStyle = TextStyle(fontSize = 13.sp),
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = {

        })
    ) {
        TextFieldDefaults.TextFieldDecorationBox(
            value = "",
            innerTextField = it,
            singleLine = true,
            enabled = true,
            interactionSource = interactionSource,
            visualTransformation = VisualTransformation.None,
            placeholder = {
                if(searchQuery == "")
                Text("Search here...", color = colorResource(R.color.Grey), fontSize = 13.sp)
            },
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.search),
                    "",
                    tint = Color.Gray
                )
            },
            trailingIcon = {
                if (searchQuery != "")
                    IconButton({
                        onSearchQueryChange("")
                    }) {
                        Icon(Icons.Default.Clear, "", tint = Color.Gray)
                    }


            },
            contentPadding = TextFieldDefaults.textFieldWithLabelPadding(top = 0.dp, bottom = 0.dp)

        )
    }
}


