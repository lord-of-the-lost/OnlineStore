package com.example.onlinestore.core.storage

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.onlinestore.core.models.CartItemModel
import com.example.onlinestore.core.models.ProductModel


@Entity
data class UserObject(
    @PrimaryKey
    @ColumnInfo("id") val id: Int,
    @ColumnInfo("name") val name: String,
    @ColumnInfo("email") val email: String,
    @ColumnInfo("password") val password: String,
    @ColumnInfo("avatar") val avatar: String,
    @ColumnInfo("wishList") val wishList: List<ProductModel>,
    @ColumnInfo("cartList") val cartList: List<CartItemModel>,
    @ColumnInfo("searchHistory") val searchHistory: List<String>,
    @ColumnInfo("country") val country: String,
    @ColumnInfo("isManager") val isManager: Boolean,
    @ColumnInfo("isLogin") val isLogin: Boolean,
)