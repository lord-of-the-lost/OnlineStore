package com.example.onlinestore.core.models

data class UserModel(
    val id: Int,
    val name: String,
    val email: String,
    val password: String,
    val avatar: ByteArray,
    val wishList: List<ProductModel>,
    val cartList: List<CartItemModel>,
    val country: String,
    val isManager: Boolean,
)