package com.example.swiftcartapp.view.sell

import android.Manifest
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.swiftcartapp.routes.Routes
import com.example.swiftcartapp.viewmodel.login.LoginViewModel
import com.example.swiftcartapp.viewmodel.product.ProductViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun SellingPage(
    viewModel: ProductViewModel = hiltViewModel(),
    viewModelAuth: LoginViewModel = hiltViewModel(),
    navController: NavController
) {
    val context = LocalContext.current

    //auth below
    val tokenFlow = viewModelAuth.getSavedToken()
    val token by tokenFlow.collectAsState(initial = null)

    if(token == null){
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    //auth above
    val email by viewModelAuth.getEmail().collectAsState("")

    var selectImageUri by rememberSaveable { mutableStateOf<Uri?>(null) }
    var productName by rememberSaveable { mutableStateOf("") }
    var productPrice by rememberSaveable { mutableStateOf("") }
    var productQuantity by rememberSaveable { mutableStateOf("") }

    //viewmodel state
    val isUploading = viewModel.isUploading
    val uploadError = viewModel.uploadError
    val productAdded = viewModel.productAdded

    val scrollState = rememberScrollState()

    //camera below

    val cameraPermissionState = rememberPermissionState(permission = Manifest.permission.CAMERA)
    var capturedImageUri by rememberSaveable { mutableStateOf<Uri?>(null) }
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            selectImageUri = capturedImageUri
        } else {
            Toast.makeText(context, "No image captured", Toast.LENGTH_SHORT).show()
        }
    }

    fun createImageUri(): Uri? {
        val contentResolver = context.contentResolver
        val contentValues = android.content.ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, "product_${System.currentTimeMillis()}.jpg")
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        }
        return contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
    }

    //camera above

    // Choose permission string based on Android version
    val galleryPermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        Manifest.permission.READ_MEDIA_IMAGES
    } else {
        @Suppress("DEPRECATION")
        Manifest.permission.READ_EXTERNAL_STORAGE
    }

    // Use Accompanist to handle permission request/response
    val storagePermissionState = rememberPermissionState(permission = galleryPermission)

    // Image picker launcher
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            selectImageUri = uri
        } else {
            Toast.makeText(context, "No image selected", Toast.LENGTH_SHORT).show()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "SELL YOUR PRODUCT",
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Monospace,
            fontSize = 28.sp,
            modifier = Modifier
                .border(2.dp, Color.Black, RectangleShape)
                .padding(8.dp),
        )
        Spacer(modifier = Modifier.height(56.dp))
        OutlinedTextField(
            value = productName,
            onValueChange = {productName = it},
            label = {Text(text = "PRODUCT NAME", fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold)},
            modifier = Modifier
                .width(350.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = Color.Black,
                focusedLabelColor = Color.Black,
                unfocusedLabelColor = Color.Black,
                cursorColor = Color.Black,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            )
        )
        Spacer(modifier = Modifier.height(56.dp))
        OutlinedTextField(
            value = productPrice,
            onValueChange = {productPrice = it},
            label = {Text(text = "PRODUCT PRICE", fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold)},
            modifier = Modifier
                .width(350.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = Color.Black,
                focusedLabelColor = Color.Black,
                unfocusedLabelColor = Color.Black,
                cursorColor = Color.Black,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            )
        )
        Spacer(modifier = Modifier.height(56.dp))
        OutlinedTextField(
            value = productQuantity,
            onValueChange = {productQuantity = it},
            label = {Text(text = "PRODUCT QUANTITY", fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold)},
            modifier = Modifier
                .width(350.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = Color.Black,
                focusedLabelColor = Color.Black,
                unfocusedLabelColor = Color.Black,
                cursorColor = Color.Black,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            )
        )
        Spacer(modifier = Modifier.height(56.dp))

        // Show image if available
        selectImageUri?.let {
            Image(
                painter = rememberAsyncImagePainter(model = it),
                contentDescription = "Selected image",
                modifier = Modifier
                    .size(300.dp)
                    .padding(16.dp),
                contentScale = ContentScale.Crop
            )
        }

        Button(
            modifier = Modifier
                .width(350.dp),
            shape = RectangleShape,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,
                contentColor = Color.Black
            ),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp),
            border = BorderStroke(2.dp, Color.Black),
            onClick = {
                when {
                    storagePermissionState.status.isGranted -> {
                        imagePickerLauncher.launch("image/*")
                    }
                    storagePermissionState.status.shouldShowRationale -> {
                        Toast.makeText(
                            context,
                            "Permission is required to access the gallery!",
                            Toast.LENGTH_SHORT
                        ).show()
                        storagePermissionState.launchPermissionRequest()
                    }
                    else -> {
                        storagePermissionState.launchPermissionRequest()
                    }
                }
            }
        ) {
                Text(text = "SELECT IMAGE", fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
        }
        Spacer(modifier = Modifier.height(66.dp))
        Button(
            modifier = Modifier
                .width(350.dp),
            shape = RectangleShape,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,
                contentColor = Color.Black
            ),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp),
            border = BorderStroke(2.dp, Color.Black),
            onClick = {
                when {
                    cameraPermissionState.status.isGranted -> {
                        // Prepare a temp URI and launch camera
                        val uri = createImageUri()
                        capturedImageUri = uri
                        uri?.let { cameraLauncher.launch(it) }
                    }
                    cameraPermissionState.status.shouldShowRationale -> {
                        Toast.makeText(
                            context,
                            "Camera permission is required to take photos!",
                            Toast.LENGTH_SHORT
                        ).show()
                        cameraPermissionState.launchPermissionRequest()
                    }
                    else -> {
                        cameraPermissionState.launchPermissionRequest()
                    }
                }
            }
        ) {
                Text(text = "CLICK IMAGE", fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
        }
        Spacer(modifier = Modifier.height(66.dp))
        Button(
            modifier = Modifier
                .width(350.dp),
            shape = RectangleShape,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,
                contentColor = Color.Black
            ),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp),
            border = BorderStroke(2.dp, Color.Black),
            onClick = {
                if(productName.isBlank() || productPrice.isBlank() || productQuantity.isBlank() || selectImageUri == null){
                    Toast.makeText(context, "Fill all fields and pick an image!", Toast.LENGTH_SHORT).show()
                }else{
                    viewModel.submitProduct(
                        context = context,
                        name = productName,
                        price = productPrice.toDoubleOrNull() ?: 0.0,
                        quantity = productQuantity.toIntOrNull() ?: 0,
                        imageUri = selectImageUri!!,
                        seller = email!!,
                        token = token!!
                    )
                }
            },
            enabled = !isUploading
        ) {
            Text(text = if(isUploading) "UPLOADING..." else "SELL", fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
        }
        if (uploadError != null) {
            Text(
                text = uploadError,
                color = Color.Red,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
        if (productAdded) {
            Text(
                text = "Product added successfully!",
                color = Color(0xFF388E3C),
                modifier = Modifier.padding(top = 8.dp)
            )
            navController.navigate(Routes.homepage)
        }
    }
}