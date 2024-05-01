package com.example.onlinestore.navigation

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.onlinestore.core.StoreViewModel
import com.example.onlinestore.views.AuthentificationScreen.AuthViewModel
import com.example.onlinestore.views.SampleScreen
import com.example.onlinestore.views.detail.DetailScreen
import com.example.onlinestore.views.AuthentificationScreen.LoginScreen
import com.example.onlinestore.views.AuthentificationScreen.RegistrationScreen
import com.example.onlinestore.views.HomeScreen.MainScreen
import com.example.onlinestore.views.HomeScreen.networkTest.ProductItem
import com.example.onlinestore.views.search_screen.SearchScreen


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainNavigationScreen() {
    var viewModel: StoreViewModel = viewModel()
    var authViewModel: AuthViewModel = viewModel()
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
        Navigation(controller, viewModel, authViewModel, it)
    }
}

@Composable
fun Navigation(navController: NavController, viewModel: StoreViewModel, authViewModel: AuthViewModel, dp: PaddingValues) {
    NavHost(
        navController = navController as NavHostController,
        startDestination = Screen.topNavigationBar.Registration.route, modifier = Modifier.padding(dp),
        enterTransition = { EnterTransition.None},
        exitTransition = { ExitTransition.None}
    ) {

        composable(Screen.topNavigationBar.Onboarding.tRoute) {
            SampleScreen()
        }
        composable(Screen.BottomNavigation.WishList.broute) {
            SampleScreen()
        }
        composable(Screen.BottomNavigation.Home.broute) {
            MainScreen(navController, navigateToDetail = {
                navController.currentBackStackEntry?.savedStateHandle?.set("key", it)
                navController.navigate(Screen.topNavigationBar.DetailProductScreen.tRoute)
            })
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
        composable(Screen.topNavigationBar.DetailProductScreen.tRoute, enterTransition = {
            fadeIn(
                animationSpec = tween(300, easing = LinearEasing)
            ) + slideIntoContainer(
                animationSpec = tween(300, easing = EaseIn),
                towards = AnimatedContentTransitionScope.SlideDirection.Start
            )
        },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(3000, easing = LinearEasing)
                ) + slideOutOfContainer(
                    animationSpec = tween(3000, easing = EaseOut),
                    towards = AnimatedContentTransitionScope.SlideDirection.End
                )
            }) {
            val product =
                navController.previousBackStackEntry?.savedStateHandle?.get<ProductItem>("key")
                    ?: ProductItem(0, "", 0, "", emptyList(), "", "", null)
            DetailScreen(modifier = Modifier, navController = navController, product)
        }
        composable(Screen.topNavigationBar.Authorization.tRoute) {
            LoginScreen(navController, authViewModel)
        }
        composable(Screen.topNavigationBar.Registration.tRoute) {
            RegistrationScreen(navController, authViewModel)
        }
        composable(Screen.topNavigationBar.SearchResultScreen.tRoute) {
            SearchScreen()
        }
        composable(Screen.topNavigationBar.Cart.tRoute) {
            SampleScreen()
        }
    }
}
