package com.example.onlinestore.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.onlinestore.R

sealed class Screen(
    val route: String,
    val title: String,
) {
    sealed class topNavigationBar(
        val tRoute: String,
        val tTitle: String,
        val icon: ImageVector?,
        val actionIcon: Int?
    ) : Screen(tRoute, tTitle) {
        object Onboarding : topNavigationBar("onboarding", "", null, null)
        object Registration : topNavigationBar("signUp", "Sign Up", Icons.Default.ArrowBack, null)
        object Authorization : topNavigationBar("authorization", "", null, null)
        object DetailProductScreen : topNavigationBar(
            "detail_product",
            "Details product",
            Icons.Default.ArrowBack,
            R.drawable.buy
        )

        object SearchResultScreen :
            topNavigationBar(
                "search_result_screen",
                "",
                Icons.Default.ArrowBack,
                R.drawable.buy
            )

        object TermsConditions :
            topNavigationBar("terms_conditions", "", Icons.Default.ArrowBack,null)

        object AddProduct : topNavigationBar("add_product", "Add new product", null, null)
        object Cart : topNavigationBar(
            "your_cart",
            "Your Cart",
            Icons.Default.ArrowBack,
            R.drawable.buy
        )
    }
    sealed class BottomNavigation(
        val broute: String,
        val bTitle: String,
        val icon: Int,
        val color: Color,
        val actionIcon: Int?
    ) :
        Screen(broute, bTitle) {
        object Home : BottomNavigation("home", "Home", R.drawable.ic_home, Color(0xFF67C4A7), R.drawable.buy)
        object WishList :
            BottomNavigation("wish_list", "Wishlist", R.drawable.ic_wishlist, Color(0xFF67C4A7), R.drawable.buy)

        object Manager :
            BottomNavigation("manager", "Manager", R.drawable.ic_manager, Color(0xFF67C4A7),null)

        object Account :
            BottomNavigation("account", "Account", R.drawable.ic_account, Color(0xFF67C4A7),null)
    }
}
val topScreens = listOf(
    Screen.topNavigationBar.AddProduct,
    Screen.topNavigationBar.DetailProductScreen,
    Screen.topNavigationBar.SearchResultScreen,
    Screen.topNavigationBar.Authorization,
    Screen.topNavigationBar.Cart,
    Screen.topNavigationBar.Onboarding,
    Screen.topNavigationBar.Registration,
    Screen.topNavigationBar.TermsConditions
)
val bottomScreen = listOf(
    Screen.BottomNavigation.Home,
    Screen.BottomNavigation.WishList,
    Screen.BottomNavigation.Manager,
    Screen.BottomNavigation.Account
)