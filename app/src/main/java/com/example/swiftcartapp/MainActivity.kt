package com.example.swiftcartapp

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.swiftcartapp.routes.Routes
import com.example.swiftcartapp.ui.theme.SwiftCartAppTheme
import com.example.swiftcartapp.view.MainScreen
import com.example.swiftcartapp.view.login.LoginScreen
import com.example.swiftcartapp.view.product.ProductScreen
import com.example.swiftcartapp.view.profile.ProfileScreen
import com.example.swiftcartapp.view.register.RegisterScreen
import com.example.swiftcartapp.view.sell.SellingPage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MainScreen()
        }
    }
}