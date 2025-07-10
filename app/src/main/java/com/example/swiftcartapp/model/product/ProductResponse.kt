package com.example.swiftcartapp.model.product

data class ProductResponse(
    val id: Long?,
    val productName: String,
    val price: Double,
    val quantity: Int,
    val imageUrl: String,
    val message: String?
)
