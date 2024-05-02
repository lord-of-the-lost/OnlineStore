package com.example.onlinestore.core.storage

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.onlinestore.core.models.CategoryModel
import com.example.onlinestore.core.models.Converters

@Entity
@TypeConverters(Converters::class)
data class ProductObject(
    @PrimaryKey
    @ColumnInfo("id") val id: Int,
    @ColumnInfo("title") val title: String,
    @ColumnInfo("price") val price: Int,
    @ColumnInfo("description") val description: String,
    @ColumnInfo("images") val images: List<String>,
    @ColumnInfo("creationAt") val creationAt: String,
    @ColumnInfo("updatedAt") val updatedAt: String,
    @ColumnInfo("category") val category: CategoryModel
)