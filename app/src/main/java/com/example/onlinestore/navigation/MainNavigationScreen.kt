package com.example.onlinestore.navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.onlinestore.core.StoreViewModel
import com.example.onlinestore.views.SampleScreen
import com.example.onlinestore.views.detail.DetailScreen
import com.example.onlinestore.views.manager_screen.ManagerScreen
import com.example.onlinestore.views.onboarding.OnboardingScreen
import com.example.onlinestore.views.AuthentificationScreen.LoginScreen
import com.example.onlinestore.views.AuthentificationScreen.RegistrationScreen
import com.example.onlinestore.views.SampleScreen


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainNavigationScreen() {
    var viewModel: StoreViewModel = viewModel()
    val controller: NavController = rememberNavController()
    val navBackStackEntry by controller.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val scaffoldState = rememberScaffoldState()

    var title = topScreens.firstOrNull() { it.route == currentRoute }?.title
        ?: bottomScreen.firstOrNull() { it.route == currentRoute }?.title ?: "Unknown"

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            if (title != "Home") {
                TopNavigationBar(title, { controller.navigateUp() }, controller)
            }
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
        startDestination = Screen.topNavigationBar.Registration.route, modifier = Modifier.padding(dp)
    ) {

        composable(Screen.topNavigationBar.Onboarding.tRoute) {
            SampleScreen()
        }
        composable(Screen.BottomNavigation.WishList.broute) {
            SampleScreen()
        }
        composable(Screen.BottomNavigation.Home.broute) {
           SampleScreen()
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
            LoginScreen(navController)
        }
        composable(Screen.topNavigationBar.Registration.tRoute) {
            RegistrationScreen(navController)
        }
        composable(Screen.topNavigationBar.SearchResultScreen.tRoute) {
            SampleScreen()
        }
        composable(Screen.topNavigationBar.Cart.tRoute) {
            SampleScreen()
        }
    }

}
