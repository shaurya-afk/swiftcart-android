package com.example.swiftcartapp.view.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.swiftcartapp.routes.Routes
import com.example.swiftcartapp.viewmodel.login.LoginViewModel

// Monochrome palette
private val MonoBg = Color(0xFFF8F8F8)
private val MonoSurface = Color(0xFFFFFFFF)
private val MonoPrimary = Color(0xFF000000)
private val MonoOnPrimary = Color.White
private val CardShadow = Color(0x14000000)

@Composable
fun ProfileScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    navController: NavController
) {

    val email by viewModel.getEmail().collectAsState(initial = null)
    val userData by viewModel.user_data.collectAsState()

    LaunchedEffect(email) {
        val emailValue = email
        if (!emailValue.isNullOrBlank()) {
            viewModel.getUser(emailValue)
        }
    }

    val name = userData?.name ?: "..."
    val _email = userData?.email?:"..."

    Surface(
        color = MonoBg,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            // Profile Icon
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "Profile",
                tint = Color(0xFF222222),
                modifier = Modifier
                    .size(110.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Name
            Text(
                text = name,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = MonoPrimary,
                fontFamily = FontFamily.SansSerif
            )
            // Email
            Text(
                text = _email,
                fontSize = 17.sp,
                color = MonoPrimary,
                fontFamily = FontFamily.SansSerif
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    navController.navigate(Routes.sellingPage)
                },
                modifier = Modifier
                    .width(350.dp),
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                ),
                border = BorderStroke(1.dp, Color.Black)
            ) {
                Text(
                    text = "SELL YOUR PRODUCT",
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace,
                    fontSize = 20.sp
                )
            }

            Spacer(modifier = Modifier.height(38.dp))

            // Logout Button
            Button(
                onClick = {
                    viewModel.logout()
                    navController.navigate(Routes.login) {
                        popUpTo(Routes.homepage) { inclusive = true }
                    }
                },
                modifier = Modifier
                    .width(350.dp),
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red,
                    contentColor = Color.Black
                ),
                border = BorderStroke(1.dp, Color.Black)
            ) {
                Text(
                    text = "LOGOUT",
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace,
                    fontSize = 20.sp
                )
            }
        }
    }
}

@Composable
fun StatCard(title: String, value: Int) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(90.dp),
        shape = RoundedCornerShape(16.dp),
        color = MonoSurface,
        shadowElevation = 6.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 18.dp, vertical = 14.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color(0xFF222222),
                fontFamily = FontFamily.SansSerif
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = value.toString(),
                fontWeight = FontWeight.Black,
                fontSize = 28.sp,
                color = MonoPrimary,
                fontFamily = FontFamily.SansSerif
            )
        }
    }
}
