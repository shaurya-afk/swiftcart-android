package com.example.swiftcartapp.view

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.swiftcartapp.routes.Routes
import com.example.swiftcartapp.view.login.LoginScreen
import com.example.swiftcartapp.view.product.ProductScreen
import com.example.swiftcartapp.view.product.bottomNavItems
import com.example.swiftcartapp.view.profile.ProfileScreen
import com.example.swiftcartapp.view.register.EmailVerificationScreen
import com.example.swiftcartapp.view.register.RegisterScreen
import com.example.swiftcartapp.view.sell.SellingPage

private val MonoBg = Color(0xFFFFFFFF)

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    var selectedItem by remember { mutableStateOf(0) }
    selectedItem = bottomNavItems.indexOfFirst { it.route == currentRoute }

    Scaffold(
        bottomBar = {
            NavigationBar(containerColor = MonoBg) {
                bottomNavItems.forEachIndexed { index, item ->
                    NavigationBarItem(
                        icon = { Icon(item.icon, contentDescription = item.label) },
                        label = { Text(item.label) },
                        selected = selectedItem == index,
                        onClick = {
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color.Black,
                            indicatorColor = MonoBg
                        )
                    )
                }
            }
        },
        containerColor = MonoBg
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Routes.homepage,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Routes.homepage) {
                ProductScreen(navController = navController)
            }
            composable(Routes.sellingPage) {
                SellingPage(navController = navController)
            }
            composable(Routes.profile) {
                ProfileScreen(navController = navController)
            }
            composable(Routes.login) {
                LoginScreen(navController = navController)
            }
            composable(Routes.register) {
                RegisterScreen(navController = navController)
            }
            composable(Routes.emailVerify) {
                EmailVerificationScreen(navController = navController)
            }
        }
    }
}