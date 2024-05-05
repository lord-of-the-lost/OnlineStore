package com.example.onlinestore.navigation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.onlinestore.core.StoreViewModel

@Composable
fun BottomNavigationBar(navController: NavController, viewModel: StoreViewModel) {
    val isUserManager by viewModel.isUserManager.collectAsState()
    BottomNavigation(
        backgroundColor = Color.White,
        modifier = Modifier.height(96.dp)
    ) {
        val currentRoute = currentRoute(navController)
        bottomScreen.forEach { screen ->
            if (screen.route != Screen.BottomNavigation.Manager.broute || isUserManager) {
                val isSelected = currentRoute == screen.route
                BottomNavigationItem(
                    icon = {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                painter = painterResource(id = screen.icon),
                                contentDescription = null,
                                colorFilter = if (isSelected) ColorFilter.tint(screen.color) else null
                            )
                            Text(screen.bTitle)
                        }
                    },
                    selectedContentColor = screen.color,
                    selected = isSelected,
                    onClick = {
                        navController.navigate(screen.route) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                )
            }
        }
    }
}

@Composable
fun currentRoute(navController: NavController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}

fun Modifier.bottomBorder(border: BorderStroke): Modifier = composed {
    this.then(
        Modifier.border(
            border = border,
            shape = RoundedCornerShape(
                topStart = 12.dp,
                topEnd = 12.dp,
                bottomStart = 0.dp,
                bottomEnd = 0.dp
            )
        )
    )
}
