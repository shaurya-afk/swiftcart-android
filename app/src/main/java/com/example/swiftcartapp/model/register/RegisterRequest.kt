package com.example.swiftcartapp.model.register

data class RegisterRequest(
    val email:String,
    val password:String,
    val name:String,
    val role:List<String>
)
