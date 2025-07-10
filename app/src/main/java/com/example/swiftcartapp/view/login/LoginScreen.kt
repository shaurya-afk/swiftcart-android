package com.example.swiftcartapp.view.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.swiftcartapp.R
import com.example.swiftcartapp.routes.Routes
import com.example.swiftcartapp.model.login.LoginRequest
import com.example.swiftcartapp.viewmodel.login.LoginViewModel
import androidx.compose.foundation.gestures.detectTapGestures

// 🎨 Black & White Monochrome Palette
private val MonoBg = Color(0xFFFFFFFF)
private val MonoSurface = Color(0xFFF5F5F5)
private val MonoPrimary = Color(0xFF000000)
private val MonoPrimaryDark = Color(0xFF232323)
private val MonoAccent = Color(0xFF343434)
private val MonoOnPrimary = Color.White
private val MonoOnSurface = Color(0xFF232323)
private val MonoOutline = Color(0xFFC0C0C0)

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    navController: NavController,
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val isLoading by viewModel.isLoading.collectAsState()
    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()
    val isPressed by interactionSource.collectIsPressedAsState()

    // Check for saved token and navigate if present
    val tokenFlow = viewModel.getSavedToken()
    val token by tokenFlow.collectAsState(initial = null)
    if (token != null) {
        navController.navigate(Routes.homepage) {
            popUpTo(Routes.login) { inclusive = true }
        }
    }

    Surface(
        color = MonoBg,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Swift Cart",
                fontSize = 38.sp,
                fontWeight = FontWeight.Black,
                color = MonoPrimary,
                fontFamily = FontFamily.SansSerif
            )
            Spacer(modifier = Modifier.height(32.dp))

            Image(
                painter = painterResource(id = R.drawable.auth_image),
                contentDescription = "login image",
                modifier = Modifier.size(140.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Welcome Back",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MonoPrimaryDark,
                fontFamily = FontFamily.SansSerif
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Login to your account",
                color = MonoOnSurface,
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(24.dp))
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text(text = "Email", color = MonoPrimaryDark) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = MonoPrimary,
                    unfocusedTextColor = MonoPrimary,
                    disabledTextColor = MonoPrimary,
                    errorTextColor = MonoPrimary,
                    focusedBorderColor = MonoAccent,
                    unfocusedBorderColor = MonoOutline,
                    cursorColor = MonoPrimary,
                    focusedLabelColor = MonoAccent,
                    unfocusedLabelColor = MonoOutline
                )
            )

            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text(text = "Password", color = MonoPrimaryDark) },
                visualTransformation = PasswordVisualTransformation(),
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = MonoPrimary,
                    unfocusedTextColor = MonoPrimary,
                    disabledTextColor = MonoPrimary,
                    errorTextColor = MonoPrimary,
                    focusedBorderColor = MonoAccent,
                    unfocusedBorderColor = MonoOutline,
                    cursorColor = MonoPrimary,
                    focusedLabelColor = MonoAccent,
                    unfocusedLabelColor = MonoOutline
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Enhanced Login Button with hover, press, and loading effects
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onTap = {
                                if (!isLoading) {
                                    viewModel.loginUser(LoginRequest(email, password))
                                }
                            }
                        )
                    }
                    .graphicsLayer {
                        scaleX = if (isPressed) 0.97f else 1f
                        scaleY = if (isPressed) 0.97f else 1f
                        alpha = if (isHovered) 0.85f else 1f
                    }
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(48.dp)
                            .align(Alignment.Center),
                        color = MonoAccent,
                        strokeWidth = 3.dp
                    )
                } else {
                    Button(
                        onClick = { viewModel.loginUser(LoginRequest(email, password)) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        shape = androidx.compose.foundation.shape.RoundedCornerShape(0.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MonoPrimary,
                            contentColor = MonoOnPrimary
                        ),
                        interactionSource = interactionSource,
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
                    ) {
                        Text(
                            text = "Login",
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.SansSerif,
                            fontSize = 18.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Don't have an account? SignUp",
                fontSize = 15.sp,
                color = MonoAccent,
                fontWeight = FontWeight.Medium,
                modifier = Modifier
                    .clickable { navController.navigate(Routes.register) }
                    .padding(4.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLoginScreen() {
    LoginScreen(navController = rememberNavController())
}