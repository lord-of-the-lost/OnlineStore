@file:Suppress("UNUSED_EXPRESSION")

package com.example.onlinestore.navigation

import android.annotation.SuppressLint
import android.os.Build
import androidx.navigation.compose.composable
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.example.onlinestore.core.StoreViewModel
import com.example.onlinestore.views.AuthentificationScreen.LoginScreen
import com.example.onlinestore.views.AuthentificationScreen.RegistrationScreen
import com.example.onlinestore.views.CartScreen.CartScreen
import com.example.onlinestore.views.Favorite_Screen.FavoriteScreen
import com.example.onlinestore.views.HomeScreen.MainScreen
import com.example.onlinestore.views.change_picture.Camera
import com.example.onlinestore.views.detail.DetailScreen
import com.example.onlinestore.views.manager_screen.ManagerScreen
import com.example.onlinestore.views.manager_screen.add_screen.AddProduct
import com.example.onlinestore.views.manager_screen.create_category.CreateCategory
import com.example.onlinestore.views.manager_screen.deleteScreen.DeleteScreen
import com.example.onlinestore.views.manager_screen.delete_category.DeleteCategory
import com.example.onlinestore.views.manager_screen.update_Screen.UpdateScreen
import com.example.onlinestore.views.manager_screen.update_category.UpdateCategory
import com.example.onlinestore.views.onboarding.OnboardingScreen
import com.example.onlinestore.views.profile_screen.ProfileScreen
import com.example.onlinestore.views.search_screen.SearchScreen
import com.example.onlinestore.views.terms_conditions.Terms


@RequiresApi(Build.VERSION_CODES.P)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainNavigationScreen(viewModel: StoreViewModel) {
    val controller: NavController = rememberNavController()
    val scaffoldState = rememberScaffoldState()
    val currentUser by viewModel.currentUser.collectAsState()
    val isUserLoaded by viewModel.isUserLoaded.collectAsState()
    if (isUserLoaded) {
        val startDestination = if (currentUser != null) {
            Screen.BottomNavigation.Home.route
        } else {
            Screen.NavigationItem.Onboarding.route
        }
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
                        BottomNavigationBar(controller, viewModel)
                }
            }
        ) {
            Navigation(controller, viewModel, startDestination, it)
        }
    } else {
        CircularProgressIndicator()
    }
}

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun Navigation(
    navController: NavController,
    viewModel: StoreViewModel,
    startDestination: String,
    dp: PaddingValues
) {
    NavHost(
        navController = navController as NavHostController,
        startDestination = startDestination,
        modifier = Modifier.padding(dp),
        enterTransition = { fadeIn(animationSpec = tween(1)) },
        exitTransition = { fadeOut(animationSpec = tween(1)) },
        popEnterTransition = { fadeIn(animationSpec = tween(1)) },
        popExitTransition = { fadeOut(animationSpec = tween(1)) }
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
            ManagerScreen(Modifier,navController)
        }
        composable(Screen.BottomNavigation.Account.broute) {
            ProfileScreen(navController, viewModel)
        }
        composable(Screen.NavigationItem.AddProduct.tRoute) {
            AddProduct(viewModel)
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
            SearchScreen(navController,viewModel)
        }
        composable(Screen.NavigationItem.Cart.tRoute) {
            CartScreen(viewModel)
        }
        composable(Screen.NavigationItem.UpdateProduct.route){
            UpdateScreen(viewModel)
        }
        composable(Screen.NavigationItem.DeleteProduct.route){
            DeleteScreen(viewModel)
        }
        composable(Screen.NavigationItem.CreateCategory.route){
            CreateCategory(viewModel)
        }
        composable(Screen.NavigationItem.UpdateCategory.route){
            UpdateCategory(viewModel)
        }
        composable(Screen.NavigationItem.DeleteCategory.route){
            DeleteCategory(viewModel)
        }
        composable(Screen.NavigationItem.Camera.tRoute) {
            Camera(
                viewModel,
                onBackClick = { navController.navigate(Screen.BottomNavigation.Account.broute) }
            )
        }
    }
}