package com.example.onlinestore.navigation

import androidx.compose.ui.graphics.Color
import com.example.onlinestore.R

sealed class Screen(
    val route: String,
    val iconResourceId: Int? = null,
    val activeColor: Color? = null
) {
    object Onboarding : Screen("onboarding")
    object Registration : Screen("registration")
    object Authorization : Screen("authorization")
    object DetailProductScreen : Screen("detail_product_screen")
    object SearchResultScreen : Screen("search_result_screen")
    object TermsConditions : Screen("terms_conditions")
    object AddProduct : Screen("add_product")
    object Cart : Screen("cart")
    object Home : Screen("home", R.drawable.ic_home, Color(0xFF67C4A7))
    object WishList :
        Screen("wish_list", R.drawable.ic_wishlist, Color(0xFF67C4A7))
    object Manager :
        Screen("manager", R.drawable.ic_manager, Color(0xFF67C4A7))
    object Account : Screen("account", R.drawable.ic_account, Color(0xFF67C4A7))
}