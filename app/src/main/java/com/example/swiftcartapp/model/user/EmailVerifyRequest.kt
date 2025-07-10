package com.example.swiftcartapp.model.user

data class EmailVerifyRequest(
    val email: String,
    val otp: String
)
