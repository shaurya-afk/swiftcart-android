package com.example.swiftcartapp.view.register

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.core.DataStore
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.swiftcartapp.DI.DataStoreManager
import com.example.swiftcartapp.R
import com.example.swiftcartapp.routes.Routes
import com.example.swiftcartapp.model.register.RegisterRequest
import com.example.swiftcartapp.viewmodel.register.RegisterViewModel

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
fun RegisterScreen(
    viewModel: RegisterViewModel = hiltViewModel(),
    navController: NavController
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }

    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState();

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
                contentDescription = "signup image",
                modifier = Modifier.size(140.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Welcome!",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MonoPrimaryDark,
                fontFamily = FontFamily.SansSerif
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Make a new account",
                color = MonoOnSurface,
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(24.dp))
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text(text = "Name", color = MonoPrimaryDark) },
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
            Button(
                onClick = {
                    //TODO(redirect to email verification page)
                    viewModel.registerUser(
                        RegisterRequest(
                            email = email,
                            password = password,
                            name = name,
                            role = listOf("USER")
                        )
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(0.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MonoPrimary,
                    contentColor = MonoOnPrimary
                ),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
            ) {
                Text(
                    text = "SignUp",
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace,
                    fontSize = 18.sp
                )
            }

            LaunchedEffect(uiState) {
                uiState?.let {
                    if (it == "SignUp Successful ✅") {
                        viewModel.saveEmail(email)
                        navController.navigate(Routes.emailVerify)
                    } else {
                        Toast.makeText(context, it, Toast.LENGTH_LONG).show()
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Already have an account? SignIn",
                fontSize = 15.sp,
                color = MonoAccent,
                fontWeight = FontWeight.Medium,
                modifier = Modifier
                    .clickable { navController.navigate(Routes.login) }
                    .padding(4.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    RegisterScreen(navController = rememberNavController())
}
