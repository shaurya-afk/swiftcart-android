package com.example.swiftcartapp.view.register

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.swiftcartapp.model.user.EmailVerifyRequest
import com.example.swiftcartapp.model.user.EmailVerifyResponse
import com.example.swiftcartapp.routes.Routes
import com.example.swiftcartapp.viewmodel.register.RegisterViewModel

private val MonoBg = Color(0xFFFFFFFF)
private val MonoSurface = Color(0xFFF5F5F5)
private val MonoPrimary = Color(0xFF000000)
private val MonoPrimaryDark = Color(0xFF232323)
private val MonoAccent = Color(0xFF343434)
private val MonoOnPrimary = Color.White
private val MonoOnSurface = Color(0xFF232323)
private val MonoOutline = Color(0xFFC0C0C0)

@Composable
fun EmailVerificationScreen(
    navController: NavController,
    viewModel: RegisterViewModel = hiltViewModel()
){
    val verificationResponse by viewModel.verificationResponse.collectAsState()
    val isVerified by viewModel.isVerified.collectAsState()
    val emailState by viewModel.getEmail().collectAsState("")

    var otp by remember { mutableStateOf("") }

    if (isVerified == true){
        navController.navigate(Routes.login)
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "VERIFY YOUR EMAIL",
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Monospace,
            fontSize = 28.sp,
            modifier = Modifier
                .padding(12.dp)
        )
        Spacer(modifier = Modifier.height(36.dp))
        OutlinedTextField(
            value = otp,
            onValueChange = { otp = it },
            label = { Text(text = "ENTER OTP", color = MonoPrimaryDark) },
            singleLine = true,
            modifier = Modifier
                .width(350.dp),
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
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            )
        )
        Spacer(modifier = Modifier.height(36.dp))
        Button(
            //TODO(verify the OTP from the viewmodel, backend endpoint, if OTP is verified, redirect to homepage)
            onClick = {
                viewModel.verifyOtp(
                    EmailVerifyRequest(
                        email = emailState.toString(),
                        otp = otp
                    )
                )
            },
            modifier = Modifier
                .width(350.dp)
                .height(48.dp)
                .border(1.dp, Color.Black),
            shape = RectangleShape,
            colors = ButtonDefaults.buttonColors(
                containerColor = MonoBg,
                contentColor = MonoPrimary
            ),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.5.dp)
        ) {
            Text(
                text = "SUBMIT",
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace,
                fontSize = 22.sp
            )
        }
    }
}