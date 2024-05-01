package com.example.onlinestore.core.models

data class CartItemModel(
    val id: Int,
    val product: ProductModel,
    val quantity: Int
)