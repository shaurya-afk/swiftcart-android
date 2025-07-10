package com.example.swiftcartapp.view.product

import android.graphics.Bitmap
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Scale
import com.example.swiftcartapp.R
import com.example.swiftcartapp.routes.Routes
import com.example.swiftcartapp.model.product.Product
import com.example.swiftcartapp.viewmodel.login.LoginViewModel

// 🎨 Monochrome Black & White Palette
private val MonoBg = Color(0xFFFFFFFF)          // White
private val MonoSurface = Color(0xFFF5F5F5)     // Light Gray (for cards, surfaces)
private val MonoPrimary = Color(0xFF000000)     // Black
private val MonoPrimaryDark = Color(0xFF232323) // Jet (deep gray-black)
private val MonoAccent = Color(0xFF343434)      // Jet (for buttons/accents)
private val MonoOnPrimary = Color(0xFFFFFFFF)   // White (on black)
private val MonoOnSurface = Color(0xFF232323)   // Deep gray (on light)
private val MonoOutline = Color(0xFFC0C0C0)     // Argent (light gray for borders)

@Composable
fun ProductCard(
    product: Product,
    viewModel: LoginViewModel = hiltViewModel(),
    navController: NavController
) {
    val context = LocalContext.current
    val imageSizeDp = 80.dp
    val imageSizePx = with(androidx.compose.ui.platform.LocalDensity.current) { imageSizeDp.roundToPx() }
    val isTokenValid by viewModel.isTokenValid

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp)),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MonoSurface)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(product.imageUrl)
                    .size(imageSizePx, imageSizePx)
                    .scale(Scale.FILL)
                    .allowHardware(false)
                    .bitmapConfig(Bitmap.Config.RGB_565)
                    .build(),
                contentDescription = product.name,
                contentScale = ContentScale.Crop,
                placeholder = painterResource(R.drawable.placeholder),
                error = painterResource(R.drawable.error),
                modifier = Modifier
                    .size(imageSizeDp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(Color.LightGray)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = product.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    fontFamily = FontFamily.Monospace,
                    color = MonoOnSurface,
                    maxLines = 2
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "₹${product.price}",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    color = MonoPrimary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Box(
                    modifier = Modifier
                        .border(
                            BorderStroke(1.dp, MonoOutline),
                            RoundedCornerShape(4.dp)
                        )
                        .padding(horizontal = 8.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = "${product.quantity} units available",
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Normal,
                        fontSize = 13.sp,
                        color = MonoPrimary
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row {
//                    Button(
//                        onClick = {
//                            // TODO: add to cart feature
//                            viewModel.isAuthenticated()
//                        },
//                        modifier = Modifier
//                            .height(36.dp),
//                        shape = RectangleShape,
//                        colors = ButtonDefaults.buttonColors(
//                            containerColor = Color.White,
//                            contentColor = Color.Black
//                        ),
//                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp),
//                        border = BorderStroke(2.dp, Color.Black)
//                    ) {
//                        Text("ADD TO CART", fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
//                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Button(
                        onClick = {
                            // TODO: buy now feature
                        },
                        modifier = Modifier
                            .height(36.dp),
                        shape = RectangleShape,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = Color.Black
                        ),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp),
                        border = BorderStroke(2.dp, Color.Black)
                    ) {
                        Text("BUY NOW", fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
                    }
                }
            }
        }
    }
}
