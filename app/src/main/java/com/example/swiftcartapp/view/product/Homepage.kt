package com.example.swiftcartapp.view.product

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.swiftcartapp.model.product.Product
import com.example.swiftcartapp.routes.Routes
import com.example.swiftcartapp.view.server_error.ServerErrorScreen
import com.example.swiftcartapp.viewmodel.login.LoginViewModel
import com.example.swiftcartapp.viewmodel.product.ProductViewModel

// 🎨 Monochrome Black & White Palette
private val MonoBg = Color(0xFFFFFFFF)          // White
private val MonoSurface = Color(0xFFF5F5F5)     // Light Gray (for cards, surfaces)
private val MonoPrimary = Color(0xFF000000)     // Black
private val MonoPrimaryDark = Color(0xFF232323) // Jet (deep gray-black)
private val MonoAccent = Color(0xFF343434)      // Jet (for buttons/accents)
private val MonoOnPrimary = Color(0xFFFFFFFF)   // White (on black)
private val MonoOnSurface = Color(0xFF232323)   // Deep gray (on light)
private val MonoOutline = Color(0xFFC0C0C0)     // Argent (light gray for borders)

data class BottomNavItem(
    val label: String,
    val icon: ImageVector,
    val route: String
)

val bottomNavItems = listOf(
    BottomNavItem("Home", Icons.Default.Home, Routes.homepage),
    BottomNavItem("Sell", Icons.Default.ShoppingCart, Routes.sellingPage),
    BottomNavItem("Profile", Icons.Default.Person, Routes.profile)
)

@Composable
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
fun ProductScreen(
    viewModel: ProductViewModel = hiltViewModel(),
    viewModel_auth: LoginViewModel = hiltViewModel(),
    navController: NavController
) {
    val context = LocalContext.current

    val products by viewModel.productResponse.collectAsState(initial = emptyList())
    val isError by viewModel.isError.collectAsState()
    val tokenFlow = viewModel_auth.getSavedToken()
    val token by tokenFlow.collectAsState(initial = null)

    val isTokenValid by viewModel_auth.isTokenValid

    if(isTokenValid == false && token != null){
        Toast.makeText(context, "Token Expired", Toast.LENGTH_SHORT).show()
        navController.navigate(Routes.login)
    }

    var selectedItem by remember { mutableStateOf(0) }

    val email by viewModel_auth.getEmail().collectAsState(initial = null)
    val userData by viewModel_auth.user_data.collectAsState()

    LaunchedEffect(email) {
        val emailValue = email
        if (!emailValue.isNullOrBlank()) {
            viewModel_auth.getUser(emailValue)
        }
    }

    val name = userData?.name ?: "..."

    LaunchedEffect(Unit) { viewModel.getProducts() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "SWIFT CART",
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Bold,
                            fontSize = 28.sp,
                            color = MonoPrimary
                        )
                        Button(
                            onClick = {
                                if (token != null) {
                                    navController.navigate(Routes.profile) {
                                        popUpTo(navController.graph.startDestinationId) { inclusive = true }
                                    }
                                } else {
                                    navController.navigate(Routes.login)
                                }
                            },
                            shape = RectangleShape,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MonoOnPrimary,
                                contentColor = MonoPrimaryDark
                            ),
                            border = BorderStroke(2.dp, Color.Black),
                            elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp),
                            modifier = Modifier
                                .height(44.dp)
                                .width(100.dp)
                                .padding(8.dp),
                            contentPadding = PaddingValues(4.dp)
                        ) {
                            val displayText = if (token != null) name else "LOGIN"
                            Text(
                                text = displayText,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Monospace
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        },
        containerColor = MonoBg
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MonoBg)
                .padding(paddingValues)
        ) {
            when {
                isError -> {
                    Column(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        ServerErrorScreen()
                    }
                }
                products == null -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = MonoAccent
                    )
                }
                products!!.isEmpty() -> {
                    Text(
                        text = "No Products Available",
                        color = MonoPrimaryDark,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        fontFamily = FontFamily.Monospace,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                else -> {
                    LazyColumn(
                        contentPadding = PaddingValues(vertical = 16.dp, horizontal = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(products ?: emptyList()) { productResponse ->
                            val uiProduct = Product(
                                name = productResponse.productName,
                                price = productResponse.price,
                                quantity = productResponse.quantity,
                                imageUrl = productResponse.imageUrl
                            )
                            ProductCard(
                                product = uiProduct,
                                navController = navController
                            )
                        }
                    }
                }
            }
        }
    }
}
