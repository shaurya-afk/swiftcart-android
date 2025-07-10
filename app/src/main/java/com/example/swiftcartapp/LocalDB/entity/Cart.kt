package com.example.swiftcartapp.LocalDB.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_items")
data class Cart (
    @PrimaryKey(autoGenerate = true)
    val productId: Int = 0
)