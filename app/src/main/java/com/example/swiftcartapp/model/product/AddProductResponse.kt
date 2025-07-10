package com.example.swiftcartapp.model.product

data class AddProductResponse(
    val name: String,
    val price: Double,
    val quantity: Int,
    val imageUrl: String
)