package com.example.onlinestore.navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.onlinestore.core.StoreViewModel
import com.example.onlinestore.views.AuthentificationScreen.LoginScreen
import com.example.onlinestore.views.AuthentificationScreen.RegistrationScreen
import com.example.onlinestore.views.CartScreen.CartScreen
import com.example.onlinestore.views.Favorite_Screen.FavoriteScreen
import com.example.onlinestore.views.HomeScreen.MainScreen
import com.example.onlinestore.views.add_screen.AddProduct
import com.example.onlinestore.views.detail.DetailScreen
import com.example.onlinestore.views.manager_screen.ManagerScreen
import com.example.onlinestore.views.onboarding.OnboardingScreen
import com.example.onlinestore.views.profile_screen.ProfileScreen
import com.example.onlinestore.views.search_screen.SearchScreen
import com.example.onlinestore.views.terms_conditions.Terms


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainNavigationScreen(viewModel: StoreViewModel) {
    val controller: NavController = rememberNavController()
    val scaffoldState = rememberScaffoldState()
    val route = currentRoute(controller)
    val screenWithScaffold = allScreen.firstOrNull { it.route == route }?.withScaffold

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            if (screenWithScaffold == true) {
                TopNavigationBar({ controller.navigateUp() }, controller)
            }
        },
        bottomBar = {
            bottomScreen.forEach { screen ->
                if (currentRoute(controller) == screen.broute)
                    BottomNavigationBar(controller)
            }
        }
    ) {
        Navigation(controller, viewModel, it)
    }
}

@Composable
fun Navigation(
    navController: NavController,
    viewModel: StoreViewModel,
    dp: PaddingValues
) {
    NavHost(
        navController = navController as NavHostController,
        startDestination = Screen.BottomNavigation.Home.route,
        modifier = Modifier.padding(dp)
    ) {
        composable(Screen.NavigationItem.Onboarding.route) {
            OnboardingScreen(Modifier, navController)
        }
        composable(Screen.BottomNavigation.WishList.route) {
            FavoriteScreen(navController, viewModel)
        }
        composable(Screen.BottomNavigation.Home.route) {
            MainScreen(navController, viewModel)
        }
        composable(Screen.BottomNavigation.Manager.broute) {
            ManagerScreen()
        }
        composable(Screen.BottomNavigation.Account.broute) {
            ProfileScreen(navController, viewModel)
        }
        composable(Screen.NavigationItem.AddProduct.tRoute) {
            AddProduct()
        }
        composable(Screen.NavigationItem.TermsConditions.route) {
            Terms()
        }
        composable(Screen.NavigationItem.DetailProductScreen.route) {
            DetailScreen(viewModel)
        }
        composable(Screen.NavigationItem.Authorization.tRoute) {
            LoginScreen(navController, viewModel)
        }
        composable(Screen.NavigationItem.Registration.tRoute) {
            RegistrationScreen(navController, viewModel)
        }
        composable(Screen.NavigationItem.SearchResultScreen.tRoute) {
            SearchScreen(navController)
        }
        composable(Screen.NavigationItem.Cart.tRoute) {
            CartScreen(viewModel)
        }
    }
}