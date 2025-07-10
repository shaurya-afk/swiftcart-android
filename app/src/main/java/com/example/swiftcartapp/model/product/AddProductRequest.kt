package com.example.swiftcartapp.model.product

data class AddProductRequest(
    val name: String,
    val price: Double,
    val quantity: Int,
    val imageUrl: String,
    val seller: String
)