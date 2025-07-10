package com.example.swiftcartapp.view.buy

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.swiftcartapp.model.product.Product
import com.example.swiftcartapp.view.product.ProductCard

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
fun BuyScreen(){
    Column (
        modifier = Modifier
            .padding(18.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text("BUY PRODUCT", fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace, fontSize = 30.sp)
        Spacer(modifier = Modifier.height(16.dp))
        
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewBuyScreen(){
    BuyScreen()
}