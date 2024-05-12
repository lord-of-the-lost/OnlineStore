package com.example.onlinestore.core.storage

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.onlinestore.core.models.CartItemModel
import com.example.onlinestore.core.models.Converters
import com.example.onlinestore.core.models.ProductModel


@Entity
@TypeConverters(Converters::class)
data class UserObject(
    @PrimaryKey
    @ColumnInfo("id") val id: Int,
    @ColumnInfo("name") val name: String,
    @ColumnInfo("email") val email: String,
    @ColumnInfo("password") val password: String,
    @ColumnInfo("avatar") val avatar: ByteArray,
    @ColumnInfo("wishList") val wishList: List<ProductModel>,
    @ColumnInfo("cartList") val cartList: List<CartItemModel>,
    @ColumnInfo("searchHistory") val searchHistory: List<String>,
    @ColumnInfo("country") val country: String,
    @ColumnInfo("isManager") val isManager: Boolean,
)