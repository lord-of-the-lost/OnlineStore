package com.example.onlinestore.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.onlinestore.R

sealed class Screen(
    val route: String,
    val title: String,
    val withScaffold:Boolean
) {
    sealed class NavigationItem(
        val tRoute: String,
        val tTitle: String,
        val icon: ImageVector?,
        val actionIcon: Int?,
        val addScaffold:Boolean
    ) : Screen(tRoute, tTitle,addScaffold) {
        object Onboarding : NavigationItem("onboarding", "", null, null,false)
        object Registration : NavigationItem("signUp", "Sign Up", Icons.Default.ArrowBack, null,true)
        object Authorization : NavigationItem("authorization", "", null, null,false)
        object DetailProductScreen : NavigationItem(
            "detail_product",
            "Details product",
            Icons.Default.ArrowBack,
            R.drawable.buy,
            true
        )

        object SearchResultScreen :
            NavigationItem(
                "search_result_screen",
                "SearchResult",
                Icons.Default.ArrowBack,
                R.drawable.buy,
                true
            )
        object UpdateProduct:
                NavigationItem(
                    "update_product",
                    "Update Product",
                    Icons.Default.ArrowBack,
                    null,
                    true
                )

        object TermsConditions :
            NavigationItem("terms_conditions", "Terms & Conditions", Icons.Default.ArrowBack,null,true)

        object AddProduct : NavigationItem("add_product", "Add new product", Icons.Default.ArrowBack, null,true)
        object Cart : NavigationItem(
            "your_cart",
            "Your Cart",
            Icons.Default.ArrowBack,
            R.drawable.buy,
            true
        )
        object Camera : NavigationItem(
            "camera_open",
            "",
            null,
            null,
            false
        )

    }
    sealed class BottomNavigation(
        val broute: String,
        val bTitle: String,
        val icon: Int,
        val color: Color,
        val actionIcon: Int?,
        val addScaffold: Boolean
    ) :
        Screen(broute, bTitle,addScaffold) {
        object Home : BottomNavigation("home", "Home", R.drawable.ic_home, Color(0xFF67C4A7), R.drawable.buy,false)
        object WishList :
            BottomNavigation("wish_list", "Wishlist", R.drawable.ic_wishlist, Color(0xFF67C4A7), R.drawable.buy,true)

        object Manager :
            BottomNavigation("manager", "Manager", R.drawable.ic_manager, Color(0xFF67C4A7),null,true)

        object Account :
            BottomNavigation("account", "Account", R.drawable.ic_account, Color(0xFF67C4A7),null,true)
    }
}
val topScreens = listOf(
    Screen.NavigationItem.AddProduct,
    Screen.NavigationItem.DetailProductScreen,
    Screen.NavigationItem.SearchResultScreen,
    Screen.NavigationItem.Authorization,
    Screen.NavigationItem.Cart,
    Screen.NavigationItem.Onboarding,
    Screen.NavigationItem.Registration,
    Screen.NavigationItem.TermsConditions,
    Screen.NavigationItem.Camera,
    Screen.NavigationItem.UpdateProduct
)
val bottomScreen = listOf(
    Screen.BottomNavigation.Home,
    Screen.BottomNavigation.WishList,
    Screen.BottomNavigation.Manager,
    Screen.BottomNavigation.Account
)
val allScreen = topScreens + bottomScreen