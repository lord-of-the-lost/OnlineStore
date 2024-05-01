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
import com.example.onlinestore.views.HomeScreen.network.model.ProductItem
import com.example.onlinestore.views.AuthentificationScreen.AuthViewModel
import com.example.onlinestore.views.SampleScreen
import com.example.onlinestore.views.detail.DetailScreen
import com.example.onlinestore.views.AuthentificationScreen.LoginScreen
import com.example.onlinestore.views.AuthentificationScreen.RegistrationScreen
import com.example.onlinestore.views.HomeScreen.MainScreen
import com.example.onlinestore.views.onboarding.OnboardingScreen
import com.example.onlinestore.views.search_screen.SearchScreen


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainNavigationScreen() {
    val viewModel: StoreViewModel = viewModel()
    val authViewModel: AuthViewModel = viewModel()
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
        Navigation(controller, viewModel, authViewModel, it)
    }
}

@Composable
fun Navigation(
    navController: NavController,
    viewModel: StoreViewModel,
    authViewModel: AuthViewModel,
    dp: PaddingValues
) {
    NavHost(
        navController = navController as NavHostController,
        startDestination = Screen.NavigationItem.Registration.route,
        modifier = Modifier.padding(dp),
    ) {

        composable(Screen.NavigationItem.Onboarding.route) {
            OnboardingScreen(Modifier,navController)
        }
        composable(Screen.BottomNavigation.WishList.route) {
            SampleScreen()
        }
        composable(Screen.BottomNavigation.Home.route) {
            MainScreen(navController, navigateToDetail = {
                navController.currentBackStackEntry?.savedStateHandle?.set("key", it)
                navController.navigate(Screen.NavigationItem.DetailProductScreen.tRoute)
            })
        }
        composable(Screen.BottomNavigation.Manager.route) {
            SampleScreen()
        }
        composable(Screen.BottomNavigation.Account.route) {
            SampleScreen()
        }
        composable(Screen.NavigationItem.AddProduct.route) {
            SampleScreen()
        }
        composable(Screen.NavigationItem.TermsConditions.route) {
            SampleScreen()
        }
        composable(Screen.NavigationItem.DetailProductScreen.route, enterTransition = {
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
        composable(Screen.NavigationItem.Authorization.tRoute) {
            LoginScreen(navController, authViewModel)
        }
        composable(Screen.NavigationItem.Registration.tRoute) {
            RegistrationScreen(navController, authViewModel)
        }
        composable(Screen.NavigationItem.SearchResultScreen.tRoute) {
            SearchScreen()
        }
        composable(Screen.NavigationItem.Cart.tRoute) {
            SampleScreen()
        }
    }
}
