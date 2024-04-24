package com.example.onlinestore.navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.onlinestore.core.StoreViewModel
import com.example.onlinestore.views.SampleScreen
import com.example.onlinestore.views.onboarding.OnboardingScreen

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainNavigationScreen(viewModel: StoreViewModel) {
    val navController = rememberNavController()
    Scaffold(
        Modifier
            .background(Color.White),
        bottomBar = {
            if (navController.currentBackStackEntryAsState().value?.destination?.route in listOf(
                    Screen.Home.route,
                    Screen.WishList.route,
                    Screen.Account.route,
                    Screen.Manager.route,
                    Screen.AddProduct.route
                )
            ) {
                BottomNavigationBar(navController)
            }
        }

    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color.White)
        ) {
            NavHost(navController, startDestination = Screen.Onboarding.route) {
                composable(Screen.Onboarding.route) {
                    OnboardingScreen(
                        Modifier
                            .fillMaxSize()
                            .background(Color.White),
                        navController
                    )
                }
                composable(Screen.WishList.route) {
                    SampleScreen()
                }
                composable(Screen.Home.route) {
                    SampleScreen()
                }
                composable(Screen.Manager.route) {
                    SampleScreen()
                }
                composable(Screen.Account.route) {
                    SampleScreen()
                }
                composable(Screen.AddProduct.route) {
                    SampleScreen()
                }
                composable(Screen.TermsConditions.route) {
                    SampleScreen()
                }
                composable(Screen.DetailProductScreen.route) {
                    SampleScreen()
                }
                composable(Screen.Authorization.route) {
                    SampleScreen()
                }
                composable(Screen.Registration.route) {
                    SampleScreen()
                }
                composable(Screen.SearchResultScreen.route) {
                    SampleScreen()
                }
                composable(Screen.Cart.route) {
                    SampleScreen()
                }
            }
        }
    }
}