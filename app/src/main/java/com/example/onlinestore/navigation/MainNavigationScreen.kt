package com.example.onlinestore.navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.onlinestore.core.StoreViewModel
import com.example.onlinestore.views.CardScreen
import com.example.onlinestore.views.DetailsScreen
import com.example.onlinestore.views.HomeScreen.MainScreen
import com.example.onlinestore.views.ManagerScreen
import com.example.onlinestore.views.ProfileScreen
import com.example.onlinestore.views.SampleScreen
import com.example.onlinestore.views.SignUpScreen
import com.example.onlinestore.views.WishListScreen

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainNavigationScreen() {
    var viewModel: StoreViewModel = viewModel()
    val controller: NavController = rememberNavController()
    val navBackStackEntry by controller.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val ScaffoldState = rememberScaffoldState()

    var title = topScreens.firstOrNull() { it.route == currentRoute }?.title
        ?: bottomScreen.firstOrNull() { it.route == currentRoute }?.title ?: "Unknown"

    Scaffold(
        scaffoldState = ScaffoldState,
        topBar = {
            TopNavigationBar(title, { controller.navigateUp() }, controller)
        },
        bottomBar = {
            bottomScreen.forEach { screen ->
                if (currentRoute == screen.broute)
                    BottomNavigationBar(controller)
            }
        }
    ) {
        Navigation(controller, viewModel, it)
    }
}

@Composable
fun Navigation(navController: NavController, viewModel: StoreViewModel, dp: PaddingValues) {
    NavHost(
        navController = navController as NavHostController,
        startDestination = Screen.BottomNavigation.Home.broute, modifier = Modifier.padding(dp)
    ) {

        composable(Screen.topNavigationBar.Onboarding.tRoute) {
            SampleScreen()
        }
        composable(Screen.BottomNavigation.WishList.broute) {
           SampleScreen()
        }
        composable(Screen.BottomNavigation.Home.broute) {
            MainScreen()
        }
        composable(Screen.BottomNavigation.Manager.broute) {
            SampleScreen()
        }
        composable(Screen.BottomNavigation.Account.broute) {
            SampleScreen()
        }
        composable(Screen.topNavigationBar.AddProduct.tRoute) {
            SampleScreen()
        }
        composable(Screen.topNavigationBar.TermsConditions.tRoute) {
            SampleScreen()
        }
        composable(Screen.topNavigationBar.DetailProductScreen.tRoute) {
            SampleScreen()
        }
        composable(Screen.topNavigationBar.Authorization.tRoute) {
            SampleScreen()
        }
        composable(Screen.topNavigationBar.Registration.tRoute) {
            SampleScreen()
        }
        composable(Screen.topNavigationBar.SearchResultScreen.tRoute) {
            SampleScreen()
        }
        composable(Screen.topNavigationBar.Cart.tRoute) {
            SampleScreen()
        }
    }

}
